package com.team11.PharmacyProject.rankingCategory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ranking-category")
public class RankingCategoryController {

    @Autowired
    private RankingCategoryService rankingService;

    @GetMapping(value = "/points/{points}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RankingCategory> getCategoryByPoints(@PathVariable("points") int points) {
        RankingCategory category = rankingService.getCategoryByPoints(points);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }
}
