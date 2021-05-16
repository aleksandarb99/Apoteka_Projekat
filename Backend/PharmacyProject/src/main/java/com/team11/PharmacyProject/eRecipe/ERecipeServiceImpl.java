package com.team11.PharmacyProject.eRecipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ERecipeState;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ERecipeServiceImpl implements ERecipeService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ERecipeRepository eRecipeRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    ModelMapper modelMapper;

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
    public ERecipe dispenseMedicine(long pharmacyId, long patientId, ERecipeDTO eRecipeDTO) {
        // TODO complex validation

        // set null fields
        Optional<Patient> patient = patientRepository.findById(eRecipeDTO.getPatientId());
        if (patient.isEmpty() || patient.get().getId() != patientId || patient.get().getPenalties() >= 3) {
            return null;
        }

        // get pharmacy
        Optional<Pharmacy> pharmacyOp = pharmacyRepository.getPharmacyByIdFetchPriceList(pharmacyId);
        if (pharmacyOp.isEmpty())
            return null;
        Pharmacy pharmacy = pharmacyOp.get();

        // get e-prescription items
        Optional<ERecipe> optionalERecipe = eRecipeRepository.findFirstByCode(eRecipeDTO.getCode());
        if (optionalERecipe.isPresent()) {
            return null;
        }

        ERecipe eRecipe = modelMapper.map(eRecipeDTO, ERecipe.class);
        List<ERecipeItem> items = eRecipe.geteRecipeItems();

        // get items in pharmacy
        List<MedicineItem> medicineItems = pharmacy.getPriceList().getMedicineItems();

        for (var item : items) {
            boolean found = false;
            for (var mi : medicineItems) {
                if (item.getMedicineCode().equals(mi.getMedicine().getCode())) {
                    found = true;
                    if (mi.getAmount() < item.getQuantity()) {
                        return null;
                    }
                    mi.setAmount(mi.getAmount() - item.getQuantity());
                    break;
                }
            }
            if (!found) {
                return null;
            }
        }

        eRecipe.setDispensingDate(System.currentTimeMillis());
        eRecipe.setPatient(patient.get());
        eRecipe.setState(ERecipeState.PROCESSED);

        // save pharmacy
        pharmacyRepository.save(pharmacy);
        // save eRecipe
        eRecipeRepository.save(eRecipe);
        return eRecipe;
    }

    @Override
    public List<ERecipeDTO> getEPrescriptionsByPatientId(Long id) {

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty() || patient.get().getPenalties() == 3)
            return null;

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
            return null;
        }

        return retVal;
    }

}
