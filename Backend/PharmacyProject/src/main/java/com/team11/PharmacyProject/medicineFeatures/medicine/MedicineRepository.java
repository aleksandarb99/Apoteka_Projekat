package com.team11.PharmacyProject.medicineFeatures.medicine;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends CrudRepository<Medicine, Long> {
}
