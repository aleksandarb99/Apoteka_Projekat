package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineReservationRepository extends JpaRepository<MedicineReservation, Long> {
}
