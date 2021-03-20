package com.team11.PharmacyProject.medicineFeatures.manufacturer;

public class Manufacturer {
   private Long id;
   private String name;

   public Manufacturer() {

   }

   public Manufacturer(Long id, String name) {
      this.id = id;
      this.name = name;
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
}