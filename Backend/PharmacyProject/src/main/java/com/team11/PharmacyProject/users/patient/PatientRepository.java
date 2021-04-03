package com.team11.PharmacyProject.users.patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("select p from Patient p where p.firstName like %?1% and p.lastName like %?2%")
    List<Patient> searchPatientsByFirstAndLastName(String firstName, String lastName);

    @Query("select p from Patient p inner join fetch p.appointments a " +
            "where a.worker.id = ?1 and a.appointmentState = 'RESERVED'"+
            " and lower(p.firstName) like lower(concat('%', ?2, '%'))" +
            " and lower(p.lastName) like lower(concat('%', ?3, '%'))" +
            " and a.startTime > ?4 and a.startTime < ?5")
    Page<Patient> getExaminedPatients(Long workerID,
                                      String firstName,
                                      String lastName,
                                      Long lowerTime,
                                      Long upperTime,
                                      Pageable pageable);
}
