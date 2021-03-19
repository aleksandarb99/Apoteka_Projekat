package com.team11.PharmacyProject.medicineFeatures.medicinePrice;

import com.team11.PharmacyProject.advertisement.Advertisement;

import java.time.LocalDate;
import java.util.List;

public class MedicinePrice {
   private Long id;
   private double price;
   private LocalDate startDate;
   private List<Advertisement> advertisemens;
}