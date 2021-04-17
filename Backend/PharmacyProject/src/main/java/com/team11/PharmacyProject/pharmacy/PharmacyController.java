package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.dto.pharmacy.PharmacyAllDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyCertainMedicineDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyCrudDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyDTO;
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
    public ResponseEntity<PharmacyDTO> getPharmacyById(@PathVariable("id") Long id) {
        Pharmacy pharmacy = pharmacyService.getPharmacyById(id);

        if (pharmacy == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        PharmacyDTO dto = convertToDto(pharmacy);
        dto.setPriceListId(pharmacy.getPriceList().getId());
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/medicine/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyCertainMedicineDTO>> getPharmaciesByMedicineId(@PathVariable("id") Long id) {
        List<Pharmacy> pharmacies = pharmacyServiceImpl.getPharmaciesByMedicineId(id);

        if (pharmacies.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<PharmacyCertainMedicineDTO> dtos = new ArrayList<>();
        for(Pharmacy p : pharmacies) {
            dtos.add(new PharmacyCertainMedicineDTO(p));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
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
    public ResponseEntity<List<PharmacyCrudDTO>> getAllPharmaciesDTO() {
        List<PharmacyCrudDTO> pharmacyCrudDTOs = pharmacyService.getAll().stream().map(this::convertToCrudDTO).collect(Collectors.toList());
        return new ResponseEntity<>(pharmacyCrudDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyDTO>> getAllPharmacies() {
        List<PharmacyDTO> list = new ArrayList<>();
        List<Pharmacy> listFromService = pharmacyService.getAll();
        for (Pharmacy p :
                listFromService) {
            list.add(convertToDto(p));
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyAllDTO>> searchPharmaciesByNameOrCity
            (@Valid @RequestParam(value = "searchValue", required = false) String searchValue) throws Exception {

        // TODO vidi kako cemo hanladati errore, da li moram rucno proverati da li je prsledjeni atribut prazan string ili predugacak, null, itd.

        List<Pharmacy> pharmacyResult = pharmacyService.searchPharmaciesByNameOrCity(searchValue);
        List<PharmacyAllDTO> pharmacyDTOS = new ArrayList<>();
        for (Pharmacy p : pharmacyResult) {
            pharmacyDTOS.add(convertToAllDto(p));
        }
        return new ResponseEntity<>(pharmacyDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PharmacyAllDTO>> filterPharmacies
            (@Valid @RequestParam(value = "gradeValue", required = false) String gradeValue,
             @RequestParam(value = "distanceValue", required = false) String distanceValue,
             @RequestParam(value = "longitude", required = false) double longitude,
             @RequestParam(value = "latitude", required = false) double latitude) throws Exception {

        // TODO vidi kako cemo hanladati errore, da li moram rucno proverati da li je prsledjeni atribut prazan string ili predugacak, null, itd.
        List<Pharmacy> pharmacyResult = pharmacyService.filterPharmacies(gradeValue, distanceValue, longitude, latitude);
        List<PharmacyAllDTO> pharmacyDTOS = new ArrayList<>();
        for (Pharmacy p : pharmacyResult) {
            pharmacyDTOS.add(convertToAllDto(p));
        }
        return new ResponseEntity<>(pharmacyDTOS, HttpStatus.OK);
    }

    private PharmacyDTO convertToDto(Pharmacy pharmacy) {
        return modelMapper.map(pharmacy, PharmacyDTO.class);
    }

    private PharmacyAllDTO convertToAllDto(Pharmacy pharmacy) {
        return modelMapper.map(pharmacy, PharmacyAllDTO.class);
    }

    private Pharmacy convertToEntity(PharmacyDTO pharmacyDto) {
        return modelMapper.map(pharmacyDto, Pharmacy.class);
    }

    private PharmacyCrudDTO convertToCrudDTO(Pharmacy pharmacy) {
        return modelMapper.map(pharmacy, PharmacyCrudDTO.class);
    }

    private Pharmacy convertCrudDTOToEntity(PharmacyCrudDTO pharmacyCrudDto) {
        return modelMapper.map(pharmacyCrudDto, Pharmacy.class);
    }
}
