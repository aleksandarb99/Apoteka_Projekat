package com.team11.PharmacyProject.medicineFeatures.medicineItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MedicineItemRepository extends JpaRepository<MedicineItem, Long> {
}
