package com.team11.PharmacyProject.rankingCategory;

import javax.persistence.*;

@Entity
public class RankingCategory {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", nullable = false)
   private String name;

   @Column(name = "pointsRequired", nullable = false)
   private int pointsRequired;

   @Column(name = "discount", nullable = false)
   private double discount;

   public RankingCategory() {}

   public RankingCategory(Long id, String name, int pointsRequired, double discount) {
      this.id = id;
      this.name = name;
      this.pointsRequired = pointsRequired;
      this.discount = discount;
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