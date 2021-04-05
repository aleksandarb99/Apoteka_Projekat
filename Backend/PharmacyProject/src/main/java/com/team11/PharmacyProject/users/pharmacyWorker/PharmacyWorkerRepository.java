package com.team11.PharmacyProject.users.pharmacyWorker;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyWorkerRepository extends JpaRepository<PharmacyWorker, Long> {
    @Query("SELECT p FROM PharmacyWorker p JOIN FETCH p.workplaces WHERE p.id = (:id)")
    PharmacyWorker findByIdAndFetchAWorkplaces(@Param("id") Long id);
}


