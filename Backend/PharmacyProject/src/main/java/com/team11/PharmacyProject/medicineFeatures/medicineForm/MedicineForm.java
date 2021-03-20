package com.team11.PharmacyProject.medicineFeatures.medicineForm;

public class MedicineForm {
   private Long id;
   private String name;

   public MedicineForm() {

   }

   public MedicineForm(Long id, String name) {
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