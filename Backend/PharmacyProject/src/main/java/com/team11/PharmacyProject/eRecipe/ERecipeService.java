package com.team11.PharmacyProject.eRecipe;

import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ERecipeService {
    ERecipeDTO getERecipe(long patientId, MultipartFile file);

    ERecipe dispenseMedicine(long pharmacyId, ERecipeDTO eRecipeDTO);
}
