package com.team11.PharmacyProject.users.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public List<Patient> searchPatientsByFirstAndLastName(String firstname, String lastname){
        return patientRepository.searchPatientsByFirstAndLastName(firstname, lastname);
    }

    public Page<Patient> getExaminedPatients(Long workerID,
                                             String firstName,
                                             String lastName,
                                             Long lowerTime,
                                             Long upperTime,Pageable pageable){
        return patientRepository.getExaminedPatients(workerID, firstName, lastName, lowerTime, upperTime, pageable);
    }

    public List<Patient> getAll(){
        return (List<Patient>) patientRepository.findAll();
    }
}
