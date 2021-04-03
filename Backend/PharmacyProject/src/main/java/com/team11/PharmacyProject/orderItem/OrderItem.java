package com.team11.PharmacyProject.orderItem;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;

import javax.persistence.*;

@Entity
public class OrderItem {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "amount", nullable = false)
   private int amount;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "medicine_id")
   private Medicine medicine;

   public OrderItem() {}

   public OrderItem(Long id, int amount, Medicine medicine) {
      this.id = id;
      this.amount = amount;
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

   public Medicine getMedicine() {
      return medicine;
   }

   public void setMedicine(Medicine medicine) {
      this.medicine = medicine;
   }
}