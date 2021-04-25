package com.team11.PharmacyProject.dto.appointment;

import com.team11.PharmacyProject.appointment.Appointment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppointmentConsultationReservationDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String startTime;
    private String endTime;
    private double price;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm");

    public AppointmentConsultationReservationDTO() {}

    public AppointmentConsultationReservationDTO(Appointment a) {
        setFirstName(a.getPatient().getFirstName());
        setLastName(a.getPatient().getLastName());
        setEmail(a.getPatient().getEmail());
        setStartTime(a.getStartTime());
        setEndTime(a.getEndTime());
        setPrice(a.getPrice());
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setStartTime(long startTime) {
        Date date = new Date(startTime);
        this.startTime = sdf.format(date);
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setEndTime(long endTime) {
        Date date = new Date(endTime);
        this.endTime = sdf.format(date);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
