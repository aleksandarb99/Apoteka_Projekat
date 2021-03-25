package com.team11.PharmacyProject.advertisement;

import com.team11.PharmacyProject.enums.AdvertisementType;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import javax.persistence.*;
import java.util.List;

@Entity
public class Advertisement {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "start_date", nullable = false)
   private Long startDate;

   @Column(name = "end_date", nullable = false)
   private Long endDate;

   @Column(name = "advertisement_text")
   private String advertisementText;

   @ManyToMany
   @JoinTable(name = "advertising", joinColumns = @JoinColumn(name = "advertisement_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "medicine_price_id", referencedColumnName = "id"))
   private List<MedicinePrice> medicineList;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "pharmacy_id")
   private Pharmacy pharmacy;

   @Column(name = "discount_percent", nullable = false)
   private double discountPercent;

   @Column(name = "type", nullable = false)
   private AdvertisementType type;

   public Advertisement() {}

   public Advertisement(Long id, Long startDate, Long endDate, String advertisementText, List<MedicinePrice> medicineList, Pharmacy pharmacy, double discountPercent, AdvertisementType type) {
      this.id = id;
      this.startDate = startDate;
      this.endDate = endDate;
      this.advertisementText = advertisementText;
      this.medicineList = medicineList;
      this.pharmacy = pharmacy;
      this.discountPercent = discountPercent;
      this.type = type;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getStartDate() {
      return startDate;
   }

   public void setStartDate(Long startDate) {
      this.startDate = startDate;
   }

   public Long getEndDate() {
      return endDate;
   }

   public void setEndDate(Long endDate) {
      this.endDate = endDate;
   }

   public String getAdvertisementText() {
      return advertisementText;
   }

   public void setAdvertisementText(String advertisementText) {
      this.advertisementText = advertisementText;
   }

   public List<MedicinePrice> getMedicineList() {
      return medicineList;
   }

   public void setMedicineList(List<MedicinePrice> medicineList) {
      this.medicineList = medicineList;
   }

   public Pharmacy getPharmacy() {
      return pharmacy;
   }

   public void setPharmacy(Pharmacy pharmacy) {
      this.pharmacy = pharmacy;
   }

   public double getDiscountPercent() {
      return discountPercent;
   }

   public void setDiscountPercent(double discountPercent) {
      this.discountPercent = discountPercent;
   }

   public AdvertisementType getType() {
      return type;
   }

   public void setType(AdvertisementType type) {
      this.type = type;
   }
}