package com.team11.PharmacyProject.users.pharmacyWorker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PharmacyWorkerRepository extends JpaRepository<PharmacyWorker, Long> {
    @Query("SELECT p FROM PharmacyWorker p JOIN FETCH p.workplaces WHERE p.id = (:id)")
    PharmacyWorker findByIdAndFetchAWorkplaces(@Param("id") Long id);

    @Query("SELECT DISTINCT p FROM PharmacyWorker p LEFT JOIN FETCH p.workplaces")
    List<PharmacyWorker> findAllAndFetchAWorkplaces();

    @Query("select w from PharmacyWorker w left join fetch w.appointmentList where w.id = ?1")
    PharmacyWorker getPharmacyWorkerForCalendar(Long workerID);

    @Query("SELECT DISTINCT d FROM PharmacyWorker d LEFT JOIN FETCH d.appointmentList a WHERE d.role.name = 'DERMATOLOGIST' AND a.appointmentState = 'FINISHED' AND a.appointmentType = 'CHECKUP'")
    List<PharmacyWorker> getDermatologistsFetchFinishedCheckups();

    @Query("SELECT DISTINCT p FROM PharmacyWorker p LEFT JOIN FETCH p.appointmentList a WHERE p.role.name = 'PHARMACIST' AND a.appointmentState = 'FINISHED' AND a.appointmentType = 'CONSULTATION'")
    List<PharmacyWorker> getPharmacistsFetchFinishedConsultations();
}