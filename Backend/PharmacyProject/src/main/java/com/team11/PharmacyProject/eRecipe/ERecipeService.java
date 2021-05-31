package com.team11.PharmacyProject.eRecipe;

import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDispenseDTO;
import com.team11.PharmacyProject.exceptions.CustomException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ERecipeService {
    ERecipeDTO getERecipe(long patientId, MultipartFile file);

    ERecipe dispenseMedicine(long patientId, ERecipeDispenseDTO eRecipeDispenseDTO) throws CustomException;

    List<ERecipeDTO> getEPrescriptionsByPatientId(Long id);
}
