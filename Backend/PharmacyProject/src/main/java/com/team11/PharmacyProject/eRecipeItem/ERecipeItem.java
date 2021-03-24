package com.team11.PharmacyProject.eRecipeItem;

import javax.persistence.*;

@Entity
public class ERecipeItem {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "medicineCode", nullable = false)
   private String medicineCode;

   @Column(name = "medicineName", nullable = false)
   private String medicineName;

   @Column(name = "quantity", nullable = false)
   private int quantity;

   public ERecipeItem() {}

   public ERecipeItem(Long id, String medicineCode, String medicineName, int quantity) {
      this.id = id;
      this.medicineCode = medicineCode;
      this.medicineName = medicineName;
      this.quantity = quantity;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getMedicineCode() {
      return medicineCode;
   }

   public void setMedicineCode(String medicineCode) {
      this.medicineCode = medicineCode;
   }

   public String getMedicineName() {
      return medicineName;
   }

   public void setMedicineName(String medicineName) {
      this.medicineName = medicineName;
   }

   public int getQuantity() {
      return quantity;
   }

   public void setQuantity(int quantity) {
      this.quantity = quantity;
   }
}