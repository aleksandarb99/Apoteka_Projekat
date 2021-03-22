package com.team11.PharmacyProject.users.patient;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.enums.UserType;
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

   public Patient(Long id, String password, String firstName, String lastName, String email, String telephone,
                  UserType userType, Address address, int points, int penalties,
                  List<MedicineReservation> medicineReservation, List<Medicine> allergies) {
      super(id, password, firstName, lastName, email, telephone, userType, address);
      this.points = points;
      this.penalties = penalties;
      this.medicineReservation = medicineReservation;
      this.allergies = allergies;
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