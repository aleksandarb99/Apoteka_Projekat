package com.team11.PharmacyProject.complaintResponse;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ComplaintResponseRepository extends JpaRepository<ComplaintResponse, Long> {

    @Query("SELECT cr FROM ComplaintResponse cr LEFT JOIN FETCH cr.complaint c WHERE c.id = ?1")
    Optional<ComplaintResponse> findByIdFetchComplaint(long complaintId);
}
