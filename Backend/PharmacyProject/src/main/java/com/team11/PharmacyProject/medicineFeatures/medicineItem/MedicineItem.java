package com.team11.PharmacyProject.medicineFeatures.medicineItem;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;

import javax.persistence.*;
import java.util.List;

@Entity
public class MedicineItem {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "amount", nullable = false)
   private int amount;

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   public List<MedicinePrice> medicinePrices;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "medicine_id")
   public Medicine medicine;

   public MedicineItem() {
   }

   public MedicineItem(Long id, int amount, List<MedicinePrice> medicinePrices, Medicine medicine) {
      this.id = id;
      this.amount = amount;
      this.medicinePrices = medicinePrices;
      this.medicine = medicine;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public int getAmount() {
      return amount;
   }

   public void setAmount(int amount) {
      this.amount = amount;
   }

   public List<MedicinePrice> getMedicinePrices() {
      return medicinePrices;
   }

   public void setMedicinePrices(List<MedicinePrice> medicinePrices) {
      this.medicinePrices = medicinePrices;
   }

   public Medicine getMedicine() {
      return medicine;
   }

   public void setMedicine(Medicine medicine) {
      this.medicine = medicine;
   }
}