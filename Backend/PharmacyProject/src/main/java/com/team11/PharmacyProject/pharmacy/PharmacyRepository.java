package com.team11.PharmacyProject.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    @Query("select p from Pharmacy p where lower(p.name) like lower(concat('%', ?1, '%')) or lower(p.address.city) like lower(concat('%', ?1, '%'))")
    List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE mi.amount > 0 and m.id = (:id)")
    List<Pharmacy> findPharmaciesByMedicineId(@Param("id") Long id);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE p.id = (:pharmacyId) and m.id = (:medicineId)")
    Pharmacy findPharmacyByPharmacyAndMedicineId(@Param("pharmacyId") Long pharmacyId, @Param("medicineId") Long medicineId);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl WHERE p.id = (:id)")
    Pharmacy getPharmacyByIdAndPriceList(@Param("id") Long id);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.workplaces wp")
    List<Pharmacy> findPharmaciesFetchWorkplaces();
}
