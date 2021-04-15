package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.dto.medicine.MedicineCrudDTO;
import com.team11.PharmacyProject.dto.medicine.MedicineDTO;
import com.team11.PharmacyProject.dto.medicine.MedicineInfoDTO;
import com.team11.PharmacyProject.email.EmailService;
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

    @Autowired
    private EmailService emailService;

    @PostMapping("/send/async")
    public String sendNotif(String name){

        //slanje emaila
        try {
            System.out.println("Thread id: " + Thread.currentThread().getId());
            emailService.sendNotificaitionAsync(name);
        }catch( Exception e ){
            return null;
        }

        return "success";
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicineInfoDTO> getMedicineById(@PathVariable("id") Long id) {
        Medicine medicine = medicineService.getMedicineById(id);

        if (medicine == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MedicineInfoDTO dto = new MedicineInfoDTO(medicine);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

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
