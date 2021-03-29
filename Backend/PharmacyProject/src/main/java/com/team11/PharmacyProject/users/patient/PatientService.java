package com.team11.PharmacyProject.users.patient;

import com.team11.PharmacyProject.pharmacy.Pharmacy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public List<Patient> searchPatientsByFirstAndLastName(String firstname, String lastname){
        return patientRepository.searchPatientsByFirstAndLastName(firstname, lastname);
    }

    public List<Patient> getAll(){
        return (List<Patient>) patientRepository.findAll();
    }
}
