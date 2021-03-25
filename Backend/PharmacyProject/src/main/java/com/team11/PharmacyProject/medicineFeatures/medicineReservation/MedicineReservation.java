package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import javax.persistence.*;

@Entity
public class MedicineReservation {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "pickup_date", nullable = false)
   private Long pickupDate;

   @Column(name = "reservationID", nullable = false)
   private Long reservationID;

   @Column(name = "state", nullable = false)
   @Enumerated(EnumType.STRING)
   private ReservationState state;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "medicine_price_id")
   private MedicinePrice medicinePrice;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "pharmacy_id")
   private Pharmacy pharmacy;

   public MedicineReservation() {}

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getPickupDate() {
      return pickupDate;
   }

   public void setPickupDate(Long pickupDate) {
      this.pickupDate = pickupDate;
   }

   public Long getReservationID() {
      return reservationID;
   }

   public void setReservationID(Long reservationID) {
      this.reservationID = reservationID;
   }

   public ReservationState getState() {
      return state;
   }

   public void setState(ReservationState state) {
      this.state = state;
   }

   public MedicinePrice getMedicinePrice() {
      return medicinePrice;
   }

   public void setMedicinePrice(MedicinePrice medicinePrice) {
      this.medicinePrice = medicinePrice;
   }

   public Pharmacy getPharmacy() {
      return pharmacy;
   }

   public void setPharmacy(Pharmacy pharmacy) {
      this.pharmacy = pharmacy;
   }
}