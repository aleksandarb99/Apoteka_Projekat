package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import java.time.LocalDate;

public class MedicineReservation {
   private Long id;
   private LocalDate pickupDate;
   private Long reservationID;
   private ReservationState state;
   private MedicinePrice medicinePrice;
   private Pharmacy pharmacy;
}