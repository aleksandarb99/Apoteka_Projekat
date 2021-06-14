package com.team11.PharmacyProject.dto.appointment;

import com.team11.PharmacyProject.appointment.Appointment;

import java.util.concurrent.TimeUnit;

public class AppointmentPatientInsightDTO {

    private Long id;
    private Long startTime;
    private Long endTime;
    private int duration;
    private double price;
    private String workerName;
    private String pharmacyName;

    public AppointmentPatientInsightDTO() {
    }

    public AppointmentPatientInsightDTO(Long id, Long startTime, Long endTime, int duration, double price, String workerName, String pharmacyName) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.price = price;
        this.workerName = workerName;
        this.pharmacyName = pharmacyName;
    }

    public AppointmentPatientInsightDTO(Appointment appointment) {
        setId(appointment.getId());
        setStartTime(appointment.getStartTime());
        setEndTime(appointment.getEndTime());
        setDuration((int) TimeUnit.MILLISECONDS.toMinutes(appointment.getEndTime() - appointment.getStartTime()));
        setPrice(appointment.getPrice());
        setWorkerName(appointment.getWorker().getFirstName() + " " + appointment.getWorker().getLastName());
        setPharmacyName(appointment.getPharmacy().getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }
}
