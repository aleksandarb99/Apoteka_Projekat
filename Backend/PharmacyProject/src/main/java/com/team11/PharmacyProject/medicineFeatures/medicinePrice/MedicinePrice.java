package com.team11.PharmacyProject.medicineFeatures.medicinePrice;

import com.team11.PharmacyProject.advertisement.Advertisement;

import javax.persistence.*;
import java.util.List;

@Entity
public class MedicinePrice {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "price", nullable = false)
   private double price;

   @Column(name = "startDate", nullable = false)
   private Long startDate;

   @ManyToMany(mappedBy = "medicineList")
   private List<Advertisement> advertisements;

   public MedicinePrice() {
   }

   public MedicinePrice(Long id, double price, Long startDate, List<Advertisement> advertisements) {
      this.id = id;
      this.price = price;
      this.startDate = startDate;
      this.advertisements = advertisements;
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

   public Long getStartDate() {
      return startDate;
   }

   public void setStartDate(Long startDate) {
      this.startDate = startDate;
   }

   public List<Advertisement> getAdvertisemens() {
      return advertisements;
   }

   public void setAdvertisemens(List<Advertisement> advertisemens) {
      this.advertisements = advertisemens;
   }
}