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

        List<RankingCategory> selected = new ArrayList<>();
        List<RankingCategory> categories = rankingRepository.findAll();

        for(RankingCategory category : categories) {
            if(category.getPointsRequired() <= points)
                selected.add(category);
        }

        if(selected.size() == 0) return null;

        List<RankingCategory> sorted = selected;
        if(selected.size() > 1)
            sorted = selected.stream().sorted(Comparator.comparingInt(RankingCategory::getPointsRequired)).collect(Collectors.toList());

        // TODO da li ovde treba sorted?
        return selected.get(selected.size() - 1);
    }

    @Override
    public List<RankingCategory> getCategories() {
        return rankingRepository.findAll(Sort.by(Sort.Direction.ASC, "pointsRequired"));
    }

    @Override
    public boolean updateCategory(RankingCategory category) {
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
