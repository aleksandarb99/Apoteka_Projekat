package com.team11.PharmacyProject.rankingCategory;

import com.team11.PharmacyProject.dto.rankingcategory.RankingCategoryDTO;
import com.team11.PharmacyProject.exceptions.CustomException;
import jdk.jfr.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/ranking-category")
public class RankingCategoryController {

    @Autowired
    private RankingCategoryService rankingService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/points/{points}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RankingCategory> getCategoryByPoints(@PathVariable("points") int points) {
        RankingCategory category = rankingService.getCategoryByPoints(points);

        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RankingCategory>> getAllCategories() {
        List<RankingCategory> rankingCategories = rankingService.getCategories();
        return new ResponseEntity<>(rankingCategories, HttpStatus.OK);
    }

    @PostMapping(value = "/")
    public ResponseEntity<String> updateCategory(@RequestBody @Valid RankingCategoryDTO categoryDTO ) {
        RankingCategory category = modelMapper.map(categoryDTO, RankingCategory.class);
        try {
            rankingService.updateCategory(category);
            return new ResponseEntity<>("Category added/updated", HttpStatus.OK);
        } catch (CustomException ce) {
            return new ResponseEntity<>(ce.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops!", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") long categoryId) {
        try {
            rankingService.deleteCategory(categoryId);
            return new ResponseEntity<>("Category deleted", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Oops!", HttpStatus.BAD_REQUEST);
        }
    }
}
