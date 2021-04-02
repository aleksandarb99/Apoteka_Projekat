package com.team11.PharmacyProject.dto;

import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;


public class AppointmentDTO {

    private Long id;

    private Long startTime;

    private Long endTime;

    private int duration;

    private AppointmentState appointmentState;

    private String info;

    private double price;

    private PharmacyWorkerDTO worker;

    private AppointmentType appointmentType;

    public AppointmentDTO(Long id, Long startTime, Long endTime, int duration,
                          AppointmentState appointmentState, String info, double price,
                          PharmacyWorkerDTO worker, AppointmentType appointmentType) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.appointmentState = appointmentState;
        this.info = info;
        this.price = price;
        this.worker = worker;
        this.appointmentType = appointmentType;
    }

    public AppointmentDTO() {
    }

    public PharmacyWorkerDTO getWorker() {
        return worker;
    }

    public void setWorker(PharmacyWorkerDTO worker) {
        this.worker = worker;
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

    public AppointmentState getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(AppointmentState appointmentState) {
        this.appointmentState = appointmentState;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

}