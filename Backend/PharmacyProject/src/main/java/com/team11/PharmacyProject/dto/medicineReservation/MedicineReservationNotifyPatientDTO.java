package com.team11.PharmacyProject.dto.medicineReservation;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.patient.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MedicineReservationNotifyPatientDTO {

    private String reservationId;
    private String firstName;
    private String lastName;
    private String email;
    private String reservationDate;
    private String pickupDate;
    private String pharmacyName;
    private String pharmacyAddress;
    private double price;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy.");

    public MedicineReservationNotifyPatientDTO() {

    }

    public MedicineReservationNotifyPatientDTO(Patient patient, Pharmacy pharmacy, MedicineReservation reservation) {
        setReservationId(reservation.getReservationID());
        setFirstName(patient.getFirstName());
        setLastName(patient.getLastName());
        setEmail(patient.getEmail());
        setReservationDate(reservation.getReservationDate());
        setPickupDate(reservation.getPickupDate());
        setPharmacyName(pharmacy.getName());
        setPharmacyAddress(pharmacy.getAddress());
        setPrice(reservation.getPrice());
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
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
        this.email = "jovansimic995@gmail.com";     // Ovo ce se izbrisati kad byude deploy
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(long reservationDate) {
        Date date = new Date(reservationDate);
        this.reservationDate = sdf.format(date);
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(long pickupDate) {
        Date date = new Date(pickupDate);
        this.pickupDate = sdf.format(date);
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getPharmacyAddress() {
        return pharmacyAddress;
    }

    public void setPharmacyAddress(String pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress;
    }

    public void setPharmacyAddress(Address pharmacyAddress) {
        this.pharmacyAddress = pharmacyAddress.getStreet() + ", " + pharmacyAddress.getCity() + ", " + pharmacyAddress.getCountry();
    }
}
