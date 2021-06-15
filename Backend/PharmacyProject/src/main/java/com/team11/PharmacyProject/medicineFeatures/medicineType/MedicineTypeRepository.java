package com.team11.PharmacyProject.medicineFeatures.medicineType;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineTypeRepository extends JpaRepository<MedicineType, Long> {
    Optional<MedicineType> findByName(String name);
}
