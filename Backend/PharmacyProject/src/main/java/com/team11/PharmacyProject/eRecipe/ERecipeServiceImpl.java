package com.team11.PharmacyProject.eRecipe;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ERecipeServiceImpl implements ERecipeService {
    @Override
    public String getERecipeFromQRCode(MultipartFile file) {
        try {
            return parseQRCode(file);
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return "";
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
