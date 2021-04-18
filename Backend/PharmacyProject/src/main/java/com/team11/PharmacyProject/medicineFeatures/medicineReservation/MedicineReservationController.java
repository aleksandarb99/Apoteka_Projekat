package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/medicine-reservation")
public class MedicineReservationController {

    @Autowired
    MedicineReservationService service;

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
}
