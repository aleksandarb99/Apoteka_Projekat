package com.team11.PharmacyProject.eRecipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ERecipeRepository extends JpaRepository<ERecipe, Long> {
    Optional<ERecipe> findFirstByCode(String code);

    @Query("SELECT e FROM ERecipe e LEFT JOIN FETCH e.pharmacy p LEFT JOIN FETCH e.patient pat WHERE pat.id = ?1")
    List<ERecipe> findByPatientId(@Param("id") Long id);
}
