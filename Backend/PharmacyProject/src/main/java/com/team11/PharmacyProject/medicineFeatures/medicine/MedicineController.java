package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.dto.MedicineCrudDTO;
import com.team11.PharmacyProject.dto.MedicineDTO;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineDTO>> getMedicines() {
        List<MedicineDTO> medicineDTOs = medicineService.getAllMedicines().stream().map(m -> mapper.map(m, MedicineDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(medicineDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/crud", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineCrudDTO>> getCrudMedicines() {
        List<MedicineCrudDTO> medicineCrudDTOs = medicineService.getAllMedicines().stream().map(this::convertToCrudDto).collect(Collectors.toList());
        return new ResponseEntity<>(medicineCrudDTOs, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addMedicine(@RequestBody MedicineCrudDTO medicineCrudDto) {
        Medicine medicine = convertToEntity(medicineCrudDto);
        if (medicineService.insertMedicine(medicine)) {
            return new ResponseEntity<>("Medicine added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMedicine(@PathVariable("id") long id) {
        if (medicineService.delete(id)) {
            return new ResponseEntity<>("Medicine deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateMedicine(@PathVariable("id") long id, @RequestBody MedicineCrudDTO medicineCrudDto) {
        Medicine medicine = convertToEntity(medicineCrudDto);
        if (medicineService.update(id, medicine)) {
            return new ResponseEntity<>("Medicine updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    private Medicine convertToEntity(MedicineCrudDTO medicineCrudDto) {
        return mapper.map(medicineCrudDto, Medicine.class);
    }

    private MedicineCrudDTO convertToCrudDto(Medicine medicine) {
        return mapper.map(medicine, MedicineCrudDTO.class);
    }
}
