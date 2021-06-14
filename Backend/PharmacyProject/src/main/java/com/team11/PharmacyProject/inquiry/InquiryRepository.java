package com.team11.PharmacyProject.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("SELECT i FROM Inquiry i JOIN FETCH i.medicineItems mi JOIN FETCH mi.medicine m WHERE i.pharmacy.id = ?1")
    List<Inquiry> findAllByPharmacyId(Long id);

    @Query("select case when count(i)> 0 then true else false end from Inquiry i where i.worker.id = ?1 and " +
            "i.medicineItems.id = ?2 and i.date > ?3")
    boolean isAlreadyQueried(Long workerID, Long medItemID, Long date);

}
