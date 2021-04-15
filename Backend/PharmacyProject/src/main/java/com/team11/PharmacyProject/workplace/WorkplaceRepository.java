package com.team11.PharmacyProject.workplace;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkplaceRepository extends CrudRepository<Workplace, Long> {

    @Query("SELECT u FROM Workplace u WHERE u.pharmacy.id = ?1 AND (u.worker.userType = 'DERMATOLOGIST' OR u.worker.userType = 'PHARMACIST')")
    Iterable<Workplace> getWorkplacesByPharmacyId(Long pharmacyId);

    @Query("SELECT u FROM Workplace u WHERE u.pharmacy.id = ?1 AND u.worker.id= ?2")
    Iterable<Workplace> getWorkplacesByPharmacyIdAndWorkerId(Long pharmacyId, Long workerID);
}

