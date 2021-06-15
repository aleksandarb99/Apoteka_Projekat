package com.team11.PharmacyProject.medicineFeatures.medicineForm;

import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/{name}")
    public ResponseEntity<?> addForm(@PathVariable String name) {
        var mf = new MedicineForm();
        mf.setName(name);
        try {
            medicineFormService.addNew(mf);
            return new ResponseEntity<>("Added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops!", HttpStatus.BAD_REQUEST);
        }

    }
}
