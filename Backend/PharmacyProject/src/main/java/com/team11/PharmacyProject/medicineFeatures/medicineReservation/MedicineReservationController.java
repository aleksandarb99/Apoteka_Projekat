package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInfoDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.email.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/medicine-reservation")
public class MedicineReservationController {

    @Autowired
    MedicineReservationService service;

    @Autowired
    ModelMapper mapper;

    @Autowired
    EmailService emailService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertMedicineReservation(@Valid @RequestBody MedicineReservationInsertDTO dto, BindingResult result) {

        if(result.hasErrors()) {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }

        MedicineReservationNotifyPatientDTO reservationDTO = service.insertMedicineReservation(dto);
        if (reservationDTO != null) {
            try {
                emailService.notifyPatientAboutReservation(reservationDTO);
            } catch (Exception e) {
                e.printStackTrace();        // Verovatno moze puci zbog nedostatka interneta, ili ako nije dozvoljeno za manje bezbedne aplikacije itd.
            }
            return new ResponseEntity<>("Reserved successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reserved-medicines/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineReservationInfoDTO>> getReservedMedicinesByPatientId(@PathVariable("id") Long id) {
        List<MedicineReservation> medicines = service.getReservedMedicinesByPatientId(id);

        if (medicines == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        List<MedicineReservationInfoDTO> reservations = medicines.stream().map(mr -> mapper.map(mr, MedicineReservationInfoDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PutMapping(value = "/cancel-reservation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> cancelReservation (@PathVariable(value="id") Long id)
    {
        if(service.cancelReservation(id)){
            return new ResponseEntity<>("canceled", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("not canceled",HttpStatus.OK);
        }
    }
}
