package com.team11.PharmacyProject.eRecipe;

import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Controller
@RequestMapping("api/e-recipes")
public class ERecipeController {

    @Autowired
    ERecipeService eRecipeService;

    @PostMapping(value = "/upload-qr")
    public ResponseEntity<?> parseQRCode(@RequestParam("file") MultipartFile file) {

        ERecipeDTO eRecipeDTO = eRecipeService.getERecipe(file);
        return new ResponseEntity<>(eRecipeDTO, HttpStatus.OK);
    }

    @PostMapping(value="/dispense-medicine/{pharmacyId}")
    public ResponseEntity<?> dispenseMedicine(@PathVariable("pharmacyId") long pharmacyId, @RequestBody @Valid ERecipeDTO eRecipeDTO) {
        boolean isDispensed = eRecipeService.dispenseMedicine(pharmacyId, eRecipeDTO);
        if (isDispensed) {
            return new ResponseEntity<>("Medicine successfully dispensed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Medicine not dispensed", HttpStatus.BAD_REQUEST);
        }
    }

}
