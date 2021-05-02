package com.team11.PharmacyProject.medicineFeatures.medicine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // TODO da li umesto ':id' treba '?'
    @Query("SELECT m FROM Medicine m JOIN FETCH m.medicineForm JOIN FETCH m.medicineType JOIN FETCH m.manufacturer WHERE m.id = (:id)")
    Medicine findByIdAndFetchFormTypeManufacturer(@Param("id") Long id);
}
