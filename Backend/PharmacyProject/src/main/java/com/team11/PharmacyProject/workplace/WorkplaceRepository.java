package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.appointment.Appointment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkplaceRepository extends CrudRepository<Workplace, Long> {

    @Query("SELECT u FROM Workplace u WHERE u.pharmacy.id = ?1")
    Iterable<Workplace> getWorkplacesByPharmacyId(Long pharmacyId);
}

