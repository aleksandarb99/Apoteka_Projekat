package com.team11.PharmacyProject.medicineFeatures.medicineItem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface MedicineItemRepository extends JpaRepository<MedicineItem, Long> {

    @Query("SELECT u FROM MedicineItem u JOIN FETCH u.medicine i  WHERE u.id = ?1")
    MedicineItem findByIdWithMedicine(long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name="javax.persistence.lock.timeout", value = "3000")})
    @Query("SELECT u FROM MedicineItem u WHERE u.id = (:id)")
    MedicineItem findByIdForTransaction(Long id);
}