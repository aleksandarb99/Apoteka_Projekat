package com.team11.PharmacyProject.medicineFeatures.medicinePrice;

import com.team11.PharmacyProject.advertisement.Advertisement;

import java.time.LocalDate;
import java.util.List;

public class MedicinePrice {
   private Long id;
   private double price;
   private LocalDate startDate;
   private List<Advertisement> advertisemens;

   public MedicinePrice(Long id, double price, LocalDate startDate, List<Advertisement> advertisemens) {
      this.id = id;
      this.price = price;
      this.startDate = startDate;
      this.advertisemens = advertisemens;
   }

   public MedicinePrice() {
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public LocalDate getStartDate() {
      return startDate;
   }

   public void setStartDate(LocalDate startDate) {
      this.startDate = startDate;
   }

   public List<Advertisement> getAdvertisemens() {
      return advertisemens;
   }

   public void setAdvertisemens(List<Advertisement> advertisemens) {
      this.advertisemens = advertisemens;
   }
}