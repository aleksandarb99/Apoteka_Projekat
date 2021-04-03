package com.team11.PharmacyProject.dto.patient;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.users.patient.Patient;

import javax.validation.constraints.NotNull;

public class PatientWorkerSearchDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private Address address;
    private int points;
    private int penalties;
    private Long appointmentStart;
    private String pharmacy;

    public PatientWorkerSearchDTO(){

    }

    public PatientWorkerSearchDTO(@NotNull Patient patient, @NotNull Appointment appointment) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getEmail();
        this.telephone = patient.getTelephone();
        this.address = patient.getAddress();
        this.points = patient.getPoints();
        this.penalties = patient.getPenalties();
        this.appointmentStart = appointment.getStartTime();
        this.pharmacy = appointment.getPharmacy().getName();
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(Long appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }
}
