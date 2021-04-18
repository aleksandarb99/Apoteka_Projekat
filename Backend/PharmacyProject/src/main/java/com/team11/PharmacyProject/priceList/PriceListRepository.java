package com.team11.PharmacyProject.priceList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    @Query("SELECT pl FROM PriceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE pl.id = (:id)")
    PriceList findByIdAndFetchMedicineItems(@Param("id") Long id);
}
