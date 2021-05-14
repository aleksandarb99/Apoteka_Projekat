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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

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
    public ERecipeDTO getERecipe(MultipartFile file) {
        return getERecipeFromQRCode(file);
    }

    private ERecipeDTO getERecipeFromQRCode(MultipartFile file) {
        try {
            String qrCodeText = parseQRCode(file);
            ERecipeDTO eRecipeDTO = objectMapper.readValue(qrCodeText, ERecipeDTO.class);
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
            return eRecipeDTO;
        } catch (IOException | NotFoundException e) {
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
    public boolean dispenseMedicine(long pharmacyId, ERecipeDTO eRecipeDTO) {
        // TODO validation
        // get pharmacy
        Optional<Pharmacy> pharmacyOp = pharmacyRepository.getPharmacyByIdFetchPriceList(pharmacyId);
        if (pharmacyOp.isEmpty())
            return false;
        Pharmacy pharmacy = pharmacyOp.get();

        // get e-prescription items
        Optional<ERecipe> optionalERecipe = eRecipeRepository.findFirstByCode(eRecipeDTO.getCode());
        if (optionalERecipe.isPresent()) {
            return false;
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
                        return false;
                    }
                    mi.setAmount(mi.getAmount() - item.getQuantity());
                    break;
                }
            }
            if (!found) {
                return false;
            }
        }

        // set null fields
        Optional<Patient> patient = patientRepository.findById(eRecipeDTO.getPatientId());
        if (patient.isEmpty()) {
            return false;
        }
        eRecipe.setDispensingDate(System.currentTimeMillis());
        eRecipe.setPatient(patient.get());
        eRecipe.setState(ERecipeState.PROCESSED);

        // save pharmacy
        pharmacyRepository.save(pharmacy);
        // save eRecipe
        eRecipeRepository.save(eRecipe);
        return true;
    }

}
