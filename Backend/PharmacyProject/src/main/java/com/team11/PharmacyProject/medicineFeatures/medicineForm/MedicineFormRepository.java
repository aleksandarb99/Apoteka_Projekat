package com.team11.PharmacyProject.medicineFeatures.medicineForm;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicineFormRepository extends JpaRepository<MedicineForm, Long> {
    Optional<MedicineForm> findByName(String name);
}
