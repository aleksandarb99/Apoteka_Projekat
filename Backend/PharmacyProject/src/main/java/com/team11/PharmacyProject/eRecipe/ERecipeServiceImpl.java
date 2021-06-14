package com.team11.PharmacyProject.eRecipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDispenseDTO;
import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ERecipeState;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemRepository;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.rankingCategory.RankingCategory;
import com.team11.PharmacyProject.rankingCategory.RankingCategoryService;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ERecipeServiceImpl implements ERecipeService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ERecipeRepository eRecipeRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private RankingCategoryService rankingCategoryService;

    @Autowired
    private MedicineItemRepository medicineItemRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ERecipeDTO getERecipe(long patientId, MultipartFile file) {
        return getERecipeFromQRCode(patientId, file);
    }

    private ERecipeDTO getERecipeFromQRCode(long patientId, MultipartFile file) {
        try {
            String qrCodeText = parseQRCode(file);
            ERecipeDTO eRecipeDTO = objectMapper.readValue(qrCodeText, ERecipeDTO.class);

            if (patientId != eRecipeDTO.getPatientId()) {
                throw new RuntimeException("Invalid patient Id");
            }

            // Set state depending on eRecipeCode
            // Database will store only REJECTED/PROCESSES prescriptions
            Optional<ERecipe> er = eRecipeRepository.findFirstByCode(eRecipeDTO.getCode());
            if (er.isPresent()) {
                eRecipeDTO.setState(er.get().getState());
                eRecipeDTO.setId(er.get().getId());
                eRecipeDTO.setDispensingDate(er.get().getDispensingDate());
            } else {
                eRecipeDTO.setState(ERecipeState.NEW);
            }

            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ERecipeDTO>> violations = validator.validate(eRecipeDTO);
            if (!violations.isEmpty()) {
                throw new RuntimeException("Invalid QR code");
            }

            return eRecipeDTO;
        } catch (Exception e) {
            // If parser cannot parse QR code, or if JSON is invalid
            ERecipeDTO er = new ERecipeDTO();
            er.setState(ERecipeState.REJECTED);
            return er;
        }
    }

    private String parseQRCode(MultipartFile file) throws IOException, NotFoundException {
        InputStream inputStream = file.getInputStream();
        BufferedImage bi = ImageIO.read(inputStream);
        BufferedImageLuminanceSource bufferedImageLuminanceSource = new BufferedImageLuminanceSource(bi);
        HybridBinarizer hybridBinarizer = new HybridBinarizer(bufferedImageLuminanceSource);
        BinaryBitmap binaryBitmap = new BinaryBitmap(hybridBinarizer);
        MultiFormatReader multiFormatReader = new MultiFormatReader();

        Result result = multiFormatReader.decode(binaryBitmap);

        return result.getText();
    }

    @Override
    @Transactional(
            rollbackFor = {CustomException.class},
            propagation = Propagation.REQUIRES_NEW
    )
    public ERecipe dispenseMedicine(long patientId, ERecipeDispenseDTO eRecipeDispenseDTO) throws CustomException {
        // validation
        Patient patient = patientRepository.findByIdAndFetchAllergiesEagerly(eRecipeDispenseDTO.getPatientId());
        if (patient == null || patient.getId() != patientId) {
            throw new CustomException("Invalid patient!");
        }

        if (patient.getPenalties() >= 3) {
            throw new CustomException("You have more than 3 penalties!");
        }

        // get pharmacy
        Optional<Pharmacy> pharmacyOp = pharmacyRepository.findById(eRecipeDispenseDTO.getPharmacyId());
        if (pharmacyOp.isEmpty())
            throw new CustomException("Invalid pharmacy!");
        Pharmacy pharmacy = pharmacyOp.get();

        // get e-prescription items
        Optional<ERecipe> optionalERecipe = eRecipeRepository.findFirstByCode(eRecipeDispenseDTO.getCode());
        if (optionalERecipe.isPresent()) {
            throw new CustomException("E-Prescription already submitted!");
        }
        ERecipe eRecipe = modelMapper.map(eRecipeDispenseDTO, ERecipe.class);
        List<ERecipeItem> items = eRecipe.geteRecipeItems();

        // get allergens
        List<Medicine> allergens = patient.getAllergies();

        for (var item : items) {
            for (var al : allergens) {
                if (item.getMedicineCode().equals(al.getCode())) {
                    throw new CustomException("Allergen found!");
                }
            }
        }

        // points achieved
        int points = 0;

        for (var item : items) {
            // pokusaj da nadjes lek u datoj apoteci i zakljucaj ga
            // ako ne postoji, izadji
            Optional<MedicineItem> optionalMedicineItem = medicineItemRepository.findByPriceListIdAndCodeFetchMedicine(eRecipeDispenseDTO.getPharmacyId(), item.getMedicineCode());
            if (optionalMedicineItem.isEmpty()) {
                throw new CustomException("Medicine not found!");
            }
            MedicineItem mi = optionalMedicineItem.get();
            if (mi.getAmount() < item.getQuantity()) {
                throw new CustomException("Medicine not in stock!");
            }
            mi.setAmount(mi.getAmount() - item.getQuantity());
            points += item.getQuantity() * mi.getMedicine().getPoints();
            medicineItemRepository.save(mi);
        }

        eRecipe.setDispensingDate(System.currentTimeMillis());
        eRecipe.setPharmacy(pharmacy);
        eRecipe.setTotalPrice(eRecipeDispenseDTO.getTotalPrice());
        eRecipe.setPoints(points);

        // calculate discount
        RankingCategory rankingCategory = rankingCategoryService.getCategoryByPoints(patient.getPoints());
        double discount = 0;
        if (rankingCategory != null) {
            discount = rankingCategory.getDiscount();
        }

        eRecipe.setTotalPriceWithDiscount(eRecipeDispenseDTO.getTotalPrice() * (1 - discount / 100));

        patient.setPoints(patient.getPoints() + points);
        eRecipe.setPatient(patient);

        eRecipe.setState(ERecipeState.PROCESSED);

        patientRepository.save(patient);
        eRecipeRepository.save(eRecipe);
        return eRecipe;
    }

    @Override
    public List<ERecipeDTO> getEPrescriptionsByPatientId(Long id) {

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty())
            throw new RuntimeException("Patient is not recognized in the database!");

        if (patient.get().getPenalties() >= 3) {
            throw new RuntimeException("You have achieved 3 penalties, this functionality is not available!");
        }

        List<ERecipeDTO> retVal = new ArrayList<>();
        File qr_folder;
        try {
            qr_folder = ResourceUtils.getFile("classpath:qr");
            for (File fileEntry : qr_folder.listFiles()) {

                FileInputStream input = new FileInputStream(fileEntry);
                MultipartFile multipartFile = new MockMultipartFile("fileItem",
                        fileEntry.getName(), "image/jpg", input);
                ERecipeDTO dto = getERecipeFromQRCode(id, multipartFile);
                if (dto.getState() != ERecipeState.REJECTED && dto.getPatientId().equals(id)) {
                    retVal.add(dto);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

        return retVal;
    }

}
