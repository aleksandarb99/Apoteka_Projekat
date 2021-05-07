package com.team11.PharmacyProject.inquiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

    @Query("SELECT i FROM Inquiry i JOIN FETCH i.medicineItems mi JOIN FETCH mi.medicine m WHERE i.pharmacy.id = ?1")
    List<Inquiry> findAllByPharmacyId(Long id);

}
