package com.team11.PharmacyProject.medicineFeatures.medicineType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/medicine-types")
public class MedicineTypeController {

    @Autowired
    MedicineTypeService medicineTypeService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineType>> getTypes() {
        return new ResponseEntity<>(medicineTypeService.getAllTypes(), HttpStatus.OK);
    }
}
