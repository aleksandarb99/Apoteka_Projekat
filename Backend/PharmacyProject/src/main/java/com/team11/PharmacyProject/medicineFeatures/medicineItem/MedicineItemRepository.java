package com.team11.PharmacyProject.medicineFeatures.medicineItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;
import java.util.List;

@Repository
public interface MedicineItemRepository extends JpaRepository<MedicineItem, Long> {

    @Query("SELECT u FROM MedicineItem u JOIN FETCH u.medicine i  WHERE u.id = ?1")
    MedicineItem findByIdWithMedicine(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT mi FROM MedicineItem mi JOIN FETCH mi.medicine m  WHERE mi.priceList.id = ?1 and m.code = ?2")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "0")})
    Optional<MedicineItem> findByPriceListIdAndCodeFetchMedicine(long id, String medicineCode);

    @QueryHints({@QueryHint(name="javax.persistence.lock.timeout", value = "3000")})
    @Query("SELECT u FROM MedicineItem u WHERE u.id = (:id)")
    MedicineItem findByIdForTransaction(Long id);
}
