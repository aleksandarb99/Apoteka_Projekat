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

   public Patient() {
   }

   public int getPoints() {
      return points;
   }

   public void setPoints(int points) {
      this.points = points;
   }

   public int getPenalties() {
      return penalties;
   }

   public void setPenalties(int penalties) {
      this.penalties = penalties;
   }

   public List<MedicineReservation> getMedicineReservation() {
      return medicineReservation;
   }

   public void setMedicineReservation(List<MedicineReservation> medicineReservation) {
      this.medicineReservation = medicineReservation;
   }

   public List<Medicine> getAllergies() {
      return allergies;
   }

   public void setAllergies(List<Medicine> allergies) {
      this.allergies = allergies;
   }
}