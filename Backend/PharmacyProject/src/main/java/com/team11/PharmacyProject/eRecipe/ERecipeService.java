package com.team11.PharmacyProject.eRecipe;

import org.springframework.web.multipart.MultipartFile;

public interface ERecipeService {
    String getERecipeFromQRCode(MultipartFile file);
}
