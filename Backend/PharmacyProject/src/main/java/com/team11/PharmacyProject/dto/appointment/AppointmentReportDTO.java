package com.team11.PharmacyProject.dto.appointment;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.AppointmentState;

public class AppointmentReportDTO {
    private Long id;

    private Long start;

    private Long end;

    private String pharmacy;

    private String patient;

    private Long pharmacyID;

    private Long patientID;

    private double price;

    public AppointmentReportDTO() {
    }

    public AppointmentReportDTO(Appointment appt){
        id = appt.getId();
        start = appt.getStartTime();
        end = appt.getEndTime();
        pharmacy = appt.getPharmacy().getName();
        patient = appt.getPatient().getFirstName() + " " + appt.getPatient().getLastName();
        pharmacyID = appt.getPharmacy().getId();
        patientID = appt.getPatient().getId();
        price = appt.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public Long getPharmacyID() {
        return pharmacyID;
    }

    public void setPharmacyID(Long pharmacyID) {
        this.pharmacyID = pharmacyID;
    }

    public Long getPatientID() {
        return patientID;
    }

    public void setPatientID(Long patientID) {
        this.patientID = patientID;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
