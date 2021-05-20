package com.team11.PharmacyProject.medicineFeatures.medicineItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineItemRepository extends JpaRepository<MedicineItem, Long> {

    @Query("SELECT u FROM MedicineItem u JOIN FETCH u.medicine i  WHERE u.id = ?1")
    MedicineItem findByIdWithMedicine(long id);

}
