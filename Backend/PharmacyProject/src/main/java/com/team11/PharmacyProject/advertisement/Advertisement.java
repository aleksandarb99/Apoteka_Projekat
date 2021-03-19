package com.team11.PharmacyProject.advertisement;

import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import java.time.LocalDate;
import java.util.List;

public class Advertisement {
   private Long id;
   private LocalDate startDate;
   private LocalDate endDate;
   private String advertisementText;
   private List<MedicinePrice> medicineList;
   private Pharmacy pharmacy;
}