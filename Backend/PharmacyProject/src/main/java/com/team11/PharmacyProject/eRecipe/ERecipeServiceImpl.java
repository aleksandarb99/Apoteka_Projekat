package com.team11.PharmacyProject.eRecipe;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.enums.ERecipeState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

@Service
public class ERecipeServiceImpl implements ERecipeService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    ERecipeRepository eRecipeRepository;

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

}
