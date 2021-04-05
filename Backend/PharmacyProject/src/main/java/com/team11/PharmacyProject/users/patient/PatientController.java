package com.team11.PharmacyProject.users.patient;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        List<Patient> patientsResult = patientService.getAll();
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p :
                patientsResult) {
            patientDTOS.add(convertToDto(p));
        }
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PatientDTO>> searchPatientsByFirstAndLastName
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
        List<PatientDTO> patientDTOS = new ArrayList<>();
        for (Patient p :
                patientsResult) {
            patientDTOS.add(convertToDto(p));
        }
        return new ResponseEntity<>(patientDTOS, HttpStatus.OK);
    }
}
