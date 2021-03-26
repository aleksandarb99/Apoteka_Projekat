package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.dto.PharmacyDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/pharmacy")
public class PharmacyController {

    @Autowired
    PharmacyService pharmacyService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PharmacyDTO> getPharmacyById(@PathVariable("id") Long id){
        Pharmacy pharmacy = pharmacyService.getPharmacyById(id);

        if(pharmacy == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(convertToDto(pharmacy), HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertPharmacy(@Valid @RequestBody PharmacyDTO pharmacyDTO) {
        Pharmacy pharmacy = convertToEntity(pharmacyDTO);
        if (pharmacyService.insertPharmacy(pharmacy)) {
            return new ResponseEntity<>("Pharmacy added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deletePharmacy(@PathVariable("id") long id) {
        if (pharmacyService.delete(id)) {
            return new ResponseEntity<>("Pharmacy deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<String> updatePharmacy(@PathVariable("id") long id, @Valid @RequestBody PharmacyDTO pharmacyDTO) {
        Pharmacy pharmacy = convertToEntity(pharmacyDTO);
        if (pharmacyService.update(id, pharmacy)) {
            return new ResponseEntity<>("Pharmacy updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyDTO>> getAllPharmaciesDTO() {
        List<PharmacyDTO> pharmacyDTOs = pharmacyService.getAll().stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(pharmacyDTOs, HttpStatus.OK);
    }

    private PharmacyDTO convertToDto(Pharmacy pharmacy) {
        return modelMapper.map(pharmacy, PharmacyDTO.class);
    }

    private Pharmacy convertToEntity(PharmacyDTO pharmacyDto) {
        return modelMapper.map(pharmacyDto, Pharmacy.class);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies(){
        List<PharmacyDTO> list = new ArrayList<>();
        List<Pharmacy> listFromService = pharmacyService.getAll();
        for (Pharmacy p:
             listFromService) {
            list.add(convertToDto(p));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
