package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.users.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyWorkerRepository extends JpaRepository<PharmacyWorker, Long> {
    @Query("select w from PharmacyWorker w left join fetch w.appointmentList where w.id = ?1")
    PharmacyWorker getPharmacyWorkerForCalendar(Long workerID);
}
