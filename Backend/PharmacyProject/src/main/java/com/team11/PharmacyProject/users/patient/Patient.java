package com.team11.PharmacyProject.users.patient;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.users.user.User;

import java.util.List;

public class Patient extends User {
   private int points;
   private int penalties;
   private List<MedicineReservation> medicineReservation;
   private List<Medicine> allergies;
}