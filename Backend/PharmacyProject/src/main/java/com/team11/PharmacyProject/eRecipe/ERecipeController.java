package com.team11.PharmacyProject.eRecipe;

import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.dto.erecipe.ERecipeDispenseDTO;
import com.team11.PharmacyProject.dto.rating.RatingGetEntitiesDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.*;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("api/e-recipes")
public class ERecipeController {

    @Autowired
    ERecipeService eRecipeService;
    @Autowired
    EmailService emailService;

    @PostMapping(value = "/upload-qr/{patientId}")
    public ResponseEntity<?> parseQRCode(@PathVariable("patientId") Long patientId, @RequestParam("file") MultipartFile file) {

        ERecipeDTO eRecipeDTO = eRecipeService.getERecipe(patientId, file);

        return new ResponseEntity<>(eRecipeDTO, HttpStatus.OK);
    }

    @PostMapping(value="/dispense-medicine/{patientId}")
    public ResponseEntity<?> dispenseMedicine(@PathVariable("patientId") long patientId, @RequestBody @Valid ERecipeDispenseDTO eRecipeDispenseDTO) {
        ERecipe recipe = eRecipeService.dispenseMedicine(patientId, eRecipeDispenseDTO);
        if (recipe != null) {
            emailService.notifyPatientAboutERecipe(recipe);
            return new ResponseEntity<>("Medicine successfully dispensed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Medicine not dispensed", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEPrescriptionsByPatientId(@PathVariable("id") Long id) {

        try {
            List<ERecipeDTO> retVal = eRecipeService.getEPrescriptionsByPatientId(id);
            return new ResponseEntity<>(retVal, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
