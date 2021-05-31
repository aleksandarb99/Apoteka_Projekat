package com.team11.PharmacyProject.rankingCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RankingCategoryServiceImpl implements RankingCategoryService {

    @Autowired
    private RankingCategoryRepository rankingRepository;

    @Override
    public RankingCategory getCategoryByPoints(int points) {

        List<RankingCategory> categories = rankingRepository.findAll(Sort.by(Sort.Direction.ASC, "pointsRequired"));

        RankingCategory cat = null;

        for(RankingCategory category : categories) {
            if(category.getPointsRequired() > points)
                break;
            cat = category;
        }

        return cat;
    }

    @Override
    public List<RankingCategory> getCategories() {
        return rankingRepository.findAll(Sort.by(Sort.Direction.ASC, "pointsRequired"));
    }

    @Override
    public boolean updateCategory(RankingCategory category) {
        if (category.getDiscount() < 0 || category.getDiscount() > 100) {
            throw new RuntimeException("Discount must be between 0 and 100");
        }
        if (category.getPointsRequired() < 0) {
            throw new RuntimeException("Points must be grater than 0");
        }

        Optional<RankingCategory> rankingCategoryOp = rankingRepository.findById(category.getId());
        RankingCategory rc = new RankingCategory();
        if (rankingCategoryOp.isPresent()) {
            rc = rankingCategoryOp.get();
        }
        rc.setName(category.getName());
        rc.setDiscount(category.getDiscount());
        rc.setPointsRequired(category.getPointsRequired());
        rankingRepository.save(rc);
        return true;
    }

    @Override
    public boolean deleteCategory(long categoryId) {
        rankingRepository.deleteById(categoryId);
        return true;
    }
}
