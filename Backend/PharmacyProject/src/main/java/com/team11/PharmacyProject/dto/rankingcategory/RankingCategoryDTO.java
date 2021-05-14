package com.team11.PharmacyProject.dto.rankingcategory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RankingCategoryDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotNull
    private int pointsRequired;

    @NotNull
    private double discount;

    public RankingCategoryDTO(Long id, @NotBlank String name, @NotNull int pointsRequired, @NotNull double discount) {
        this.id = id;
        this.name = name;
        this.pointsRequired = pointsRequired;
        this.discount = discount;
    }

    public RankingCategoryDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPointsRequired() {
        return pointsRequired;
    }

    public void setPointsRequired(int pointsRequired) {
        this.pointsRequired = pointsRequired;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
