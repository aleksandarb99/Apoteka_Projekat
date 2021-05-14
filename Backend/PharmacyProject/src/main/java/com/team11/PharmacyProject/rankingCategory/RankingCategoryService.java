package com.team11.PharmacyProject.rankingCategory;

import java.util.List;

public interface RankingCategoryService {
    RankingCategory getCategoryByPoints(int points);

    List<RankingCategory> getCategories();

    boolean updateCategory(RankingCategory category);

    boolean deleteCategory(long categoryId);
}
