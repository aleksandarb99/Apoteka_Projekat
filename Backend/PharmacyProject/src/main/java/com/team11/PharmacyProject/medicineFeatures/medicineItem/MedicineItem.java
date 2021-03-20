package com.team11.PharmacyProject.medicineFeatures.medicineItem;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;

import java.util.List;

public class MedicineItem {
   private Long id;
   private int amount;
   public List<MedicinePrice> medicinePrices;
   public Medicine medicine;
}