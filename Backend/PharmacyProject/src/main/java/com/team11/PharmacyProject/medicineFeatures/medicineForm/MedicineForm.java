package com.team11.PharmacyProject.medicineFeatures.medicineForm;

import javax.persistence.*;

@Entity
public class MedicineForm {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "name", unique = true, nullable = false)
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