package com.team11.PharmacyProject.pharmacy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    @Query("select p from Pharmacy p where lower(p.name) like lower(concat('%', ?1, '%')) or lower(p.address.city) like lower(concat('%', ?1, '%'))")
    List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue);
}
