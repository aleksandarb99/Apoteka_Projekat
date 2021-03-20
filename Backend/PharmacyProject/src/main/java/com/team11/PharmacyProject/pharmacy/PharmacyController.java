package com.team11.PharmacyProject.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController {

    @Autowired
    PharmacyService pharmacyService;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pharmacy> getPharmacyById(@PathVariable("id") Long id){
        Pharmacy pharmacy = pharmacyService.getPharmacyById(id);

        if(pharmacy == null){
            return new ResponseEntity<Pharmacy>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Pharmacy>(pharmacy, HttpStatus.OK);
    }

}
