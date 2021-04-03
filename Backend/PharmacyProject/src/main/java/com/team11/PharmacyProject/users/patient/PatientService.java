package com.team11.PharmacyProject.users.patient;

import com.team11.PharmacyProject.users.user.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public List<Patient> searchPatientsByFirstAndLastName(String firstname, String lastname){
        return patientRepository.searchPatientsByFirstAndLastName(firstname, lastname);
    }

    public Patient findOne(Long id) {
        return patientRepository.findByIdAndFetchAllergiesEagerly(id);
    }

    public boolean delete(long id, long allergy_id) {
        Patient patient = patientRepository.findByIdAndFetchAllergiesEagerly(id);
        if (patient != null) {
            if(!patient.removeAllergy(allergy_id)) return false;

            patientRepository.save(patient);
            return true;
        } else {
            return false;
        }
    }

    public List<Patient> getAll(){
        return (List<Patient>) patientRepository.findAll();
    }
}
