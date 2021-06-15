package com.team11.PharmacyProject.medicineFeatures.medicineType;

import com.team11.PharmacyProject.medicineFeatures.medicineForm.MedicineForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/{name}")
    public ResponseEntity<?> addForm(@PathVariable String name) {
        var mt = new MedicineType();
        mt.setName(name);
        try {
            medicineTypeService.addNew(mt);
            return new ResponseEntity<>("Added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops!", HttpStatus.BAD_REQUEST);
        }

    }
}
