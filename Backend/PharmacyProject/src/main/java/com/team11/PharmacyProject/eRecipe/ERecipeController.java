package com.team11.PharmacyProject.eRecipe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("api/e-recipes")
public class ERecipeController {

    @Autowired
    ERecipeService eRecipeService;

    @GetMapping(value = "/upload-qr")
    public ResponseEntity<?> parseQRCode(@RequestParam("file") MultipartFile file) {
        // TODO change to ERecipeDTO
        String data = eRecipeService.getERecipeFromQRCode(file);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

}
