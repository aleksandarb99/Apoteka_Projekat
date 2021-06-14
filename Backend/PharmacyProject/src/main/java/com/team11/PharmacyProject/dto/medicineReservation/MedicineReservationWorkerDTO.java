package com.team11.PharmacyProject.dto.medicineReservation;

import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.users.patient.Patient;

public class MedicineReservationWorkerDTO {

    private Long pickupDate;
    private Long reservationDate;
    private String reservationID;
    private String medicineName;
    private String medicineID;
    private String firstName;
    private String lastName;
    private String email;

    public MedicineReservationWorkerDTO() {
    }

    public MedicineReservationWorkerDTO(MedicineReservation r) {
        this.pickupDate = r.getPickupDate();
        this.reservationDate = r.getReservationDate();
        this.reservationID = r.getReservationID();
        this.medicineName = r.getMedicineItem().getMedicine().getName();
        this.medicineID = r.getMedicineItem().getMedicine().getCode();
    }

    public MedicineReservationWorkerDTO(MedicineReservation r, Patient p) {
        this.pickupDate = r.getPickupDate();
        this.reservationDate = r.getReservationDate();
        this.reservationID = r.getReservationID();
        this.medicineName = r.getMedicineItem().getMedicine().getName();
        this.medicineID = r.getMedicineItem().getMedicine().getCode();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.email = p.getEmail();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Long pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Long getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Long reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(String medicineID) {
        this.medicineID = medicineID;
    }
}
