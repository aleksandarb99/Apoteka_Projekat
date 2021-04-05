package com.team11.PharmacyProject.users.patient;


import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dto.patient.PatientDTO;
import com.team11.PharmacyProject.dto.patient.PatientWorkerSearchDTO;

import com.team11.PharmacyProject.dto.MedicineDTO;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.time.Instant;
import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Collections;
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

    @GetMapping(value="/all", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientDTO>> getAllPatients(){
        List<Patient> patientsResult = patientService.getAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p:
                patientsResult) {
            patientDTOS.add(convertToDto(p));
        }
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }


    private PatientWorkerSearchDTO convertPatientToWSDTOsorter(Patient pat){
        return new PatientWorkerSearchDTO(pat);
    }

    private PatientWorkerSearchDTO convertPatientToWSDTOdesc(Patient pat){
        return new PatientWorkerSearchDTO(pat, false);
    }

    @GetMapping(value = "/getExaminedPatients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientWorkerSearchDTO>> getExaminedPatients(
            Pageable pageable,
            @RequestParam(value = "workerID") Long workerID,
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "lowerTime", required = false) Long lowerTime,
            @RequestParam(value = "upperTime", required = false) Long upperTime,
            @RequestParam(value = "sortDate", required = false) String dateSorting) //dateSorting je za datum posl pregleda
    {
        //TODO promeniti workerID kad se doda login i jwt
        //TODO probati kasnije sa specifikacijama
        //TODO videti za sorter datuma, trenutno je a.startTime
        if (firstName == null){
            firstName = "";
        }
        if (lastName == null){
            lastName = "";
        }
        List<Patient> patients;
        List<PatientWorkerSearchDTO> dtos = new ArrayList<>();
        if (dateSorting == null)
        {
            Sort sorter = pageable.getSort();
            patients = patientService.getExaminedPatients(workerID, firstName, lastName, lowerTime, upperTime, sorter);
            for (Patient pat: patients) {
                dtos.add(this.convertPatientToWSDTOsorter(pat));
            }
        }
        else {
            Sort sorter = pageable.getSort().and(Sort.by("a.startTime").descending()); //dodajemo mu sorter za desc svakako
            patients = patientService.getExaminedPatients(workerID, firstName, lastName, lowerTime, upperTime, sorter);
            for (Patient pat: patients) { //probaj vec ovde reverse
                dtos.add(this.convertPatientToWSDTOdesc(pat));
            }
            if (dateSorting.equals("asc")){
                Collections.reverse(dtos);
            }
        }
//        } if (dateSorting.equalsIgnoreCase("asc"))
//        {
//            Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
//                    pageable.getSort().and(Sort.by("a.startTime").ascending()));
//
//            patients = patientService.getExaminedPatients(workerID, firstName, lastName, lowerTime, upperTime, newPageable);
//            dtos = patients.map(this::convertPatientToWSDTOasc);
//        }
//        else {
//            Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
//                    pageable.getSort().and(Sort.by("a.startTime").descending()));
//            patients = patientService.getExaminedPatients(workerID, firstName, lastName, lowerTime, upperTime, newPageable);
//            dtos = patients.map(this::convertPatientToWSDTOdesc);
//        }
        //todo kod paginacije ga ispresecaj lepo
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/getAllExaminedPatients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientWorkerSearchDTO>> getAllExaminedPatients(
            @RequestParam(value = "workerID") Long workerID)
    {
        //TODO promeniti workerID kad se doda login i jwt
        List<Patient> patients = patientService.getAllExaminedPatients(workerID);
        List<PatientWorkerSearchDTO> dtos = new ArrayList<>();
        for (Patient pat: patients) {
            dtos.add(this.convertPatientToWSDTOsorter(pat));
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
    public ResponseEntity<List<PatientDTO>> searchPatientsByFirstAndLastName
            (@RequestParam(value = "firstName", required = false) String firstName,
             @RequestParam(value = "lastName", required = false) String lastName){
        List<Patient> patientsResult;
//        if (firstName == null && lastName == null){
//            // TODO da vrati sve pacijente
//        }
        if (firstName == null){
            firstName = "";
        }
        if (lastName == null){
            lastName = "";
        }
        patientsResult = patientService.searchPatientsByFirstAndLastName(firstName, lastName);
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p:
                patientsResult) {
            patientDTOS.add(convertToDto(p));
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
}
