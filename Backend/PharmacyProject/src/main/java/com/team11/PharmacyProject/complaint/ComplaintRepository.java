package com.team11.PharmacyProject.complaint;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {

    @Query("SELECT c FROM Complaint c LEFT JOIN FETCH c.patient p WHERE p.id = ?1")
    List<Complaint> getComplaintUsingPatientIdFetchPatient(long patientId);
}
