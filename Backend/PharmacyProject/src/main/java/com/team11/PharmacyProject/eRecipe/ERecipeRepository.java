package com.team11.PharmacyProject.eRecipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ERecipeRepository extends JpaRepository<ERecipe, Long> {
    Optional<ERecipe> findFirstByCode(String code);
}
