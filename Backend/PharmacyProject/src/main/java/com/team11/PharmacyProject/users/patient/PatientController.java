package com.team11.PharmacyProject.users.patient;


import com.team11.PharmacyProject.dto.pharmacy.PharmacyInfoDTO;
import com.team11.PharmacyProject.dto.patient.PatientDTO;
import com.team11.PharmacyProject.dto.patient.PatientWorkerSearchDTO;

import com.team11.PharmacyProject.dto.medicine.MedicineDTO;
import com.team11.PharmacyProject.dto.user.PharmacyWorkerInfoDTO;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;

import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/patients")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private ModelMapper modelMapper;

    private PatientDTO convertToDto(Patient patient) {
        return modelMapper.map(patient, PatientDTO.class);
    }

    private Patient convertToEntity(PatientDTO pharmacyDto) {
        return modelMapper.map(pharmacyDto, Patient.class);
    }

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<Patient> patientsResult = patientService.getAllAndFetchAddress();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p :
                patientsResult) {
            patientDTOS.add(convertToDto(p));
        }
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/points", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPatientPoints(@PathVariable("id") Long id) {
        Patient patient = patientService.getPatient(id);

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Integer.toString(patient.getPoints()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/penalties", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getPenalties(@PathVariable("id") Long id) {
        Patient patient = patientService.getPatient(id);

        if (patient == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(Integer.toString(patient.getPenalties()), HttpStatus.OK);
    }


    @GetMapping(value = "/getExaminedPatients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientWorkerSearchDTO>> getExaminedPatients(
            Pageable pageable,
            @RequestParam(value = "workerID") Long workerID,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "lowerTime", required = false) Long lowerTime,
            @RequestParam(value = "upperTime", required = false) Long upperTime) //dateSorting je za datum posl pregleda
    {
        //TODO promeniti workerID kad se doda login i jwt
        //TODO probati kasnije sa specifikacijama
        if (firstName == null){
            firstName = "";
        }
        if (lastName == null){
            lastName = "";
        }
        if (lowerTime != null && upperTime != null){
            if (lowerTime >= upperTime){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        List<Patient> patients;
        List<PatientWorkerSearchDTO> dtos = new ArrayList<>();
        Sort sorter = pageable.getSort();
        Sort checked = Sort.unsorted();
        for (Sort.Order srt : sorter){
            if (srt.getProperty().equals("firstName")){
                checked = checked.and(srt.getDirection().toString().equalsIgnoreCase("asc")
                        ? Sort.by("firstName").ascending() : Sort.by("firstName").descending());
            }else if (srt.getProperty().equals("lastName")){
                checked = checked.and(srt.getDirection().toString().equalsIgnoreCase("asc")
                        ? Sort.by("lastName").ascending() : Sort.by("lastName").descending());
            }else if (srt.getProperty().equals("startTime")){
                checked = checked.and(srt.getDirection().toString().equalsIgnoreCase("asc")
                        ? Sort.by("a.startTime").ascending() : Sort.by("a.startTime").descending());
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        patients = patientService.getExaminedPatients(workerID, firstName, lastName, lowerTime, upperTime, checked);
        for (Patient pat: patients) {
            dtos.add(new PatientWorkerSearchDTO(pat));
        }

        //todo kod paginacije ga ispresecaj lepo
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllExaminedPatients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientWorkerSearchDTO>> getAllExaminedPatients( @RequestParam(value = "workerID") Long workerID)
    {
        //TODO promeniti workerID kad se doda login i jwt
        List<Patient> patients = patientService.getAllExaminedPatients(workerID);
        List<PatientWorkerSearchDTO> dtos = new ArrayList<>();
        for (Patient pat: patients) {
            dtos.add(new PatientWorkerSearchDTO(pat));
        }
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value="/allergies/all/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MedicineDTO>> getAllAllergiesOfPatient(@PathVariable("id") Long id){

        Patient patient = patientService.findOne(id);
        if(patient == null) return null;

        List<MedicineDTO> allergiesDTOs = new ArrayList<>();
        for (Medicine m: patient.getAllergies()) {
            allergiesDTOs.add(modelMapper.map(m, MedicineDTO.class));
        }

        return new ResponseEntity<>(allergiesDTOs, HttpStatus.OK);

    }

    @GetMapping(value="/search", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientWorkerSearchDTO>> searchPatientsByFirstAndLastName
            (@RequestParam(value = "firstName", required = false) String firstName,
             @RequestParam(value = "lastName", required = false) String lastName) {
        List<Patient> patientsResult;
//        if (firstName == null && lastName == null){
//            // TODO da vrati sve pacijente
//        }
        if (firstName == null) {
            firstName = "";
        }
        if (lastName == null) {
            lastName = "";
        }
        patientsResult = patientService.searchPatientsByFirstAndLastName(firstName, lastName);
        List<PatientWorkerSearchDTO> patientDTOS = new ArrayList<>();
        for (Patient p :
                patientsResult) {
            patientDTOS.add(new PatientWorkerSearchDTO(p, true));
        }
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }

    @DeleteMapping(value = "/allergies/{id}/{allergy_id}")
    @Validated
    public ResponseEntity<String> deleteAllergy(@PathVariable("id") long id, @PathVariable("allergy_id") long allergy_id) {
        if (patientService.deleteAllergy(id, allergy_id)) {
            return new ResponseEntity<>("Allergy deleted successfully", HttpStatus.OK);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/allergies/{id}/{allergy_id}")
    public ResponseEntity<String> addAllergy(@PathVariable("id") long id, @PathVariable("allergy_id") long allergy_id) {
        if (patientService.addAllergy(id, allergy_id)) {
            return new ResponseEntity<>("Allergy added successfully", HttpStatus.OK);
        } else {
            return null;
        }
    }

    @GetMapping(value = "/{id}/my-pharmacists")
    public ResponseEntity<List<PharmacyWorkerInfoDTO>> getMyPharmacists(@PathVariable("id") long patientId) {
        List<PharmacyWorker> pharmacists = patientService.getMyPharmacists(patientId);
        return getListResponseEntity(pharmacists);
    }

    @GetMapping(value = "/{id}/my-dermatologists")
    public ResponseEntity<List<PharmacyWorkerInfoDTO>> getMyDermatologists(@PathVariable("id") long patientId) {
        List<PharmacyWorker> dermatologists = patientService.getMyDermatologists(patientId);
        return getListResponseEntity(dermatologists);
    }

    @NotNull
    private ResponseEntity<List<PharmacyWorkerInfoDTO>> getListResponseEntity(List<PharmacyWorker> pharmacyWorkers) {
        if (pharmacyWorkers == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<PharmacyWorkerInfoDTO> pDTOs = pharmacyWorkers
                .stream()
                .map(PharmacyWorkerInfoDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(pDTOs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/my-pharmacies")
    ResponseEntity<List<PharmacyInfoDTO>> getMyPharmacies(@PathVariable("id") long patientId) {
        List<Pharmacy> pharmacies = patientService.getMyPharmacies(patientId);
        if (pharmacies == null) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        List<PharmacyInfoDTO> pDTOs = pharmacies
                .stream()
                .map(PharmacyInfoDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(pDTOs, HttpStatus.OK);
    }

    @Scheduled(cron = "${greeting.cron}")
    public void givePenaltyForNotPickedUpOrCanceledReservation() {
        patientService.givePenaltyForNotPickedUpOrCanceledReservation();
    }

    @Scheduled(cron = "${greeting.cron.firstDayInMonth}")
    public void resetPenalties() {
        patientService.resetPenalties();
    }
}
