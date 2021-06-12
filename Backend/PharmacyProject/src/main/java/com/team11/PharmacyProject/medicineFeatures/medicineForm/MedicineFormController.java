package com.team11.PharmacyProject.medicineFeatures.medicineForm;

import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medicine-forms")
public class MedicineFormController {

    @Autowired
    MedicineFormService medicineFormService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineForm>> getForms() {
        return new ResponseEntity<>(medicineFormService.getAllForms(), HttpStatus.OK);
    }
}
