package com.team11.PharmacyProject.medicineFeatures.manufacturer;

import com.team11.PharmacyProject.medicineFeatures.medicineType.MedicineType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
public class ManufacturerController {

    @Autowired
    ManufacturerService manufacturerService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Manufacturer>> getForms() {
        return new ResponseEntity<>(manufacturerService.getAllManufacturers(), HttpStatus.OK);
    }

    @PostMapping(value = "/{name}")
    public ResponseEntity<?> addForm(@PathVariable String name) {
        var mt = new Manufacturer();
        mt.setName(name);
        try {
            manufacturerService.addNew(mt);
            return new ResponseEntity<>("Added successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops!", HttpStatus.BAD_REQUEST);
        }

    }
}
