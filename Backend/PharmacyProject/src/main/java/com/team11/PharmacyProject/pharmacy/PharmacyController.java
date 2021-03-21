package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.dto.AddPharmacyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;

@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController {

    @Autowired
    PharmacyService pharmacyService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pharmacy> getPharmacyById(@PathVariable("id") Long id){
        Pharmacy pharmacy = pharmacyService.getPharmacyById(id);

        if(pharmacy == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(pharmacy, HttpStatus.OK);
    }

    @PostMapping(value="/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertPharmacy(@Valid @RequestBody AddPharmacyDTO pharmacyDTO) {
        Pharmacy pharmacy = convertToEntity(pharmacyDTO);
        if (pharmacyService.insertPharmacy(pharmacy)) {
            return new ResponseEntity<>("Pharmacy added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    private AddPharmacyDTO convertToDto(Pharmacy pharmacy) {
        return modelMapper.map(pharmacy, AddPharmacyDTO.class);
    }

    private Pharmacy convertToEntity(AddPharmacyDTO pharmacyDto) {
        return modelMapper.map(pharmacyDto, Pharmacy.class);
    }
}
