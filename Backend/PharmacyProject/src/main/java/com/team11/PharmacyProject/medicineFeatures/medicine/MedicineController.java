package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.dto.medicine.MedicineCrudDTO;
import com.team11.PharmacyProject.dto.medicine.MedicineCrudEditDTO;
import com.team11.PharmacyProject.dto.medicine.MedicineDTO;
import com.team11.PharmacyProject.dto.medicine.MedicineInfoDTO;
import com.team11.PharmacyProject.dto.rating.RatingGetEntitiesDTO;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/medicine")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MedicineInfoDTO> getMedicineById(@PathVariable("id") Long id) {
        Medicine medicine = medicineService.getMedicineById(id);

        if (medicine == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        MedicineInfoDTO dto = new MedicineInfoDTO(medicine);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/notexistingmedicinebypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<MedicineDTO>> getNotExistingMedicineFromPharmacy(@PathVariable("id") long id) {
        List<MedicineDTO> medicineDTOs = medicineService.getNotExistingMedicineFromPharmacy(id).stream().map(m -> mapper.map(m, MedicineDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(medicineDTOs, HttpStatus.OK);

    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineDTO>> getMedicines() {
        List<MedicineDTO> medicineDTOs = medicineService.getAllMedicines().stream().map(m -> mapper.map(m, MedicineDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(medicineDTOs, HttpStatus.OK);
    }

    @GetMapping(value="", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineDTO>> getFilteredMedicine(@RequestParam(value = "search") String searchParams) {
        try {
            List<Medicine> medicine = medicineService.filterMedicine(searchParams);
            List<MedicineDTO> medicineDTOs = medicine.stream().map(m -> mapper.map(m, MedicineDTO.class)).collect(Collectors.toList());
            return new ResponseEntity<>(medicineDTOs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/crud", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineCrudDTO>> getCrudMedicines() {
        List<MedicineCrudDTO> medicineCrudDTOs = medicineService.getAllMedicines().stream().map(this::convertToCrudDto).collect(Collectors.toList());
        return new ResponseEntity<>(medicineCrudDTOs, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addMedicine(@RequestBody MedicineCrudEditDTO medicineCrudDto) {
        Medicine medicine = mapper.map(medicineCrudDto, Medicine.class);
        try {
            medicineService.insertMedicine(medicine);
            return new ResponseEntity<>("Medicine added successfully", HttpStatus.OK);
        } catch(CustomException ce) {
            return new ResponseEntity<>(ce.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> deleteMedicine(@PathVariable("id") long id) {
        if (medicineService.delete(id)) {
            return new ResponseEntity<>("Medicine deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Oops!", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> updateMedicine(@PathVariable("id") long id, @RequestBody MedicineCrudEditDTO medicineCrudDto) {
        Medicine medicine = mapper.map(medicineCrudDto, Medicine.class);
        try {
            medicineService.update(id, medicine);
            return new ResponseEntity<>("Medicine updated successfully", HttpStatus.OK);
        } catch(CustomException ce) {
            return new ResponseEntity<>(ce.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}/get-pdf", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> generateMedicinePdf(@PathVariable("id") long medicineId) {
        ByteArrayInputStream medicineBis = medicineService.getMedicinePdf(medicineId);
        if (medicineBis == null)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=details.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(medicineBis));
    }

    @GetMapping(value = "/all-medicines/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<?> getReceivedMedicinesByPatientId(@PathVariable("id") Long id) {

        List<Medicine> medicines = medicineService.getReceivedMedicinesByPatientId(id);

        if (medicines == null) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

        List<RatingGetEntitiesDTO> retVal = medicines.stream().map(RatingGetEntitiesDTO::new).collect(Collectors.toList());
        return new ResponseEntity<>(retVal, HttpStatus.OK);
    }

    private MedicineCrudDTO convertToCrudDto(Medicine medicine) {
        return mapper.map(medicine, MedicineCrudDTO.class);
    }
}
