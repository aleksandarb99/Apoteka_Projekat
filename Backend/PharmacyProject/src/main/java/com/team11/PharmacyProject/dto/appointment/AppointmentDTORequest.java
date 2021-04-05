package com.team11.PharmacyProject.dto.appointment;

import com.team11.PharmacyProject.dto.pharmacy.PharmacyWorkerDTO;

import javax.validation.constraints.Min;


public class AppointmentDTORequest {

    private long startTime;
    @Min(0)
    private int duration;
    @Min(0)
    private double price;

    public AppointmentDTORequest() {
    }

    public AppointmentDTORequest(Long startTime, int duration, double price, PharmacyWorkerDTO worker) {
        this.startTime = startTime;
        this.duration = duration;
        this.price = price;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
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

}
