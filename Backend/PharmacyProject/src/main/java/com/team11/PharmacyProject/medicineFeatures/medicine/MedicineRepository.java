package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.search.SearchCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long>, MedicineRepositoryCustom {

    @Query("SELECT m FROM Medicine m JOIN FETCH m.medicineForm JOIN FETCH m.medicineType JOIN FETCH m.manufacturer WHERE m.id = (:id)")
    Medicine findByIdAndFetchFormTypeManufacturer(@Param("id") Long id);

    @Query("SELECT m FROM Medicine m JOIN FETCH m.medicineForm JOIN FETCH m.medicineType JOIN FETCH m.manufacturer LEFT JOIN FETCH m.alternativeMedicine WHERE m.id = ?1")
    Medicine findByIdAndFetchFormTypeManufacturerAlternative(@Param("id") Long id);

    @Query("SELECT m FROM Medicine m JOIN FETCH m.medicineForm JOIN FETCH m.medicineType")
    List<Medicine> findAllFetchTypeForm();

    @Query("SELECT m FROM Medicine m WHERE m.code = (:medicineCode)")
    Medicine findByMedicineCode(String medicineCode);

    @Query("SELECT DISTINCT m FROM Medicine m JOIN FETCH m.medicineForm JOIN FETCH m.medicineType JOIN FETCH m.manufacturer LEFT JOIN FETCH m.alternativeMedicine")
    List<Medicine> fetchFormTypeManufacturerAlternative();
}
