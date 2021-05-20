package com.team11.PharmacyProject.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdvertismentRepository extends JpaRepository<Advertisement, Long> {

    @Query("SELECT u FROM Advertisement u JOIN FETCH u.medicineItem i JOIN FETCH i.medicine WHERE u.pharmacy.id = ?1")
    List<Advertisement> findAll(Long id);

    @Query("SELECT u FROM Advertisement u JOIN FETCH u.medicineItem i JOIN FETCH i.medicine m WHERE u.pharmacy.id = ?1 AND m.id = ?2 AND u.type = 'SALE' AND u.startDate < ?3 AND u.endDate > ?3")
    List<Advertisement> findAllSalesWithDate(Long pharmacyId, Long medicineId, long currentTimeMillis);
}