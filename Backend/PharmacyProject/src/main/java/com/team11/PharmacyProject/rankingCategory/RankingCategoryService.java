package com.team11.PharmacyProject.rankingCategory;

import com.team11.PharmacyProject.exceptions.CustomException;

import java.util.List;

public interface RankingCategoryService {
    RankingCategory getCategoryByPoints(int points);

    List<RankingCategory> getCategories();

    void updateCategory(RankingCategory category) throws CustomException;

    void deleteCategory(long categoryId);
}
