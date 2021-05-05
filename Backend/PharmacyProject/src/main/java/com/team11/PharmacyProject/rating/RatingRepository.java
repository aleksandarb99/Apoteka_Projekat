package com.team11.PharmacyProject.rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    @Query("SELECT r FROM Rating r JOIN FETCH r.patient p WHERE r.gradedID = ?1 AND p.id = ?2 AND r.gradedType = 'DERMATOLOGIST'")
    Rating findDermatologistRatingFetchPatient(Long dId, Long pId);
}
