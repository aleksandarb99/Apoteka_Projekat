package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInfoDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationWorkerDTO;
import com.team11.PharmacyProject.email.EmailService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import java.util.Map;
import java.util.concurrent.TimeUnit;
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

    @GetMapping(value = "/report/{id}/{period}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<Map<String, Integer>> getInfoForReport(@PathVariable("period") String period, @PathVariable("id") Long pharmacyId) {
        Map<String, Integer> data = service.getInfoForReport(period, pharmacyId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> insertMedicineReservation(@Valid @RequestBody MedicineReservationInsertDTO dto, BindingResult result) {

        if(result.hasErrors()) {
            return new ResponseEntity<>("Bad request!", HttpStatus.BAD_REQUEST);
        }

        try {
            MedicineReservationNotifyPatientDTO reservationDTO = service.insertMedicineReservation(dto);
            emailService.notifyPatientAboutReservation(reservationDTO);

            return new ResponseEntity<>("Medicine is reserved successfully!", HttpStatus.OK);
        }catch (PessimisticLockingFailureException e) {
            return new ResponseEntity<>("Failed! Try again!", HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/reserved-medicines/patient/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<List<MedicineReservationInfoDTO>> getReservedMedicinesByPatientId(@PathVariable("id") Long id) {
        List<MedicineReservation> medicines = service.getReservedMedicinesByPatientId(id);

        if (medicines == null) {
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }

        List<MedicineReservationInfoDTO> reservations = medicines.stream().map(mr -> mapper.map(mr, MedicineReservationInfoDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    @PutMapping(value = "/cancel-reservation/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PATIENT')")
    public ResponseEntity<String> cancelReservation (@PathVariable(value="id") Long id)
    {

        try {
            service.cancelReservation(id);
            return new ResponseEntity<>("Reservation is canceled successfully!", HttpStatus.OK);
        }catch (PessimisticLockingFailureException e) {
            return new ResponseEntity<>("Failed! Try again!", HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/getReservedIssue", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACIST')")
    public ResponseEntity<MedicineReservationWorkerDTO> getReservedMedicine(@RequestParam("workerID") Long workerdID, @RequestParam("resID") String resID) {
        MedicineReservation medicineReservation = service.getMedicineReservationFromPharmacy(workerdID, resID);
        if (medicineReservation == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Medicine reservation with given ID not found in this pharmacy!");
        }
        Long dueDate = medicineReservation.getPickupDate();
        Long currTime = Instant.now().toEpochMilli();
        if (dueDate - currTime <= 0){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pickup due date has passed!");
        }else if (TimeUnit.MILLISECONDS.toHours(dueDate-currTime) < 24){  //manje od 24 h do izdavanja
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid reservationID! Pickup due date is less than 24h!");
        }
        MedicineReservationWorkerDTO medicineReservationWorkerDTO = new MedicineReservationWorkerDTO(medicineReservation);
        return new ResponseEntity<>(medicineReservationWorkerDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/issueMedicine", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACIST')")
    public ResponseEntity<String> issueMedicine(@RequestParam("workerID") Long workerdID, @RequestParam("resID") String resID) {
        MedicineReservationWorkerDTO dto = service.issueMedicine(workerdID, resID);
        if (dto != null){
            try {
                emailService.notifyPatientAboutPickingUpMedicine(dto);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResponseEntity<>("Succesfuly issued medicine!", HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Issue of medicine failed! Request error!", HttpStatus.BAD_REQUEST);
        }
    }
}
