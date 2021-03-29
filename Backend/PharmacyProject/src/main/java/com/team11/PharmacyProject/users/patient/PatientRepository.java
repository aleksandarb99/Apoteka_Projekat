package com.team11.PharmacyProject.users.patient;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {
    @Query("select p from Patient p where p.firstName like %?1% and p.lastName like %?2%")
    List<Patient> searchPatientsByFirstAndLastName(String firstName, String lastName);
}
