package com.team11.PharmacyProject.rankingCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RankingCategoryRepository extends JpaRepository<RankingCategory, Long> {
}
