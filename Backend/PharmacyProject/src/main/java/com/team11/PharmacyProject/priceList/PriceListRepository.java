package com.team11.PharmacyProject.priceList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

@Repository
public interface PriceListRepository extends JpaRepository<PriceList, Long> {

    @Query("SELECT pl FROM PriceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE pl.id = (:id)")
    PriceList findByIdAndFetchMedicineItems(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name="javax.persistence.lock.timeout", value = "3000")})
    @Query("SELECT pl FROM PriceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE pl.id = (:id)")
    PriceList findPriceListForTransaction(@Param("id") Long id);
}
