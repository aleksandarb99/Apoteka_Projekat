package com.team11.PharmacyProject.priceList;

import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;

import java.util.List;

public class PriceList {
   private Long id;
   private List<MedicineItem> medicineItems;

   public PriceList(Long id, List<MedicineItem> medicineItems) {
      this.id = id;
      this.medicineItems = medicineItems;
   }

   public PriceList() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public List<MedicineItem> getMedicineItems() {
      return medicineItems;
   }

   public void setMedicineItems(List<MedicineItem> medicineItems) {
      this.medicineItems = medicineItems;
   }
}