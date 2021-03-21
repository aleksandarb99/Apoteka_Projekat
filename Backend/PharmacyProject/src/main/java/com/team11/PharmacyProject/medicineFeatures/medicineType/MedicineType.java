package com.team11.PharmacyProject.medicineFeatures.medicineType;

public class MedicineType {
   private Long id;
   private String name;

   public MedicineType() {

   }

   public MedicineType(Long id, String name) {
      this.id = id;
      this.name = name;
   }

   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setName(String name) {
      this.name = name;
   }
}