package com.team11.PharmacyProject.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'DERMATOLOGIST'")
    Rating findDermatologistRatingFetchPatient(Long dId, Long pId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'PHARMACIST'")
    Rating findPharmacistRatingFetchPatient(Long pId, Long paId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'MEDICINE'")
    Rating findMedicineRatingFetchPatient(Long mId, Long paId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'PHARMACY'")
    Rating findPharmacyRatingFetchPatient(Long pId, Long paId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'DERMATOLOGIST'")
    Rating findIfRatingExistsDermatologist(Long gradedID, Long patientId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'PHARMACIST'")
    Rating findIfRatingExistsPharmacist(Long gradedID, Long patientId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'MEDICINE'")
    Rating findIfRatingExistsMedicine(Long gradedID, Long patientId);

    @Query("SELECT r FROM Rating r LEFT JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'PHARMACY'")
    Rating findIfRatingExistsPharmacy(Long gradedID, Long patientId);
}
