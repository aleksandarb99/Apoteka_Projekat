package com.team11.PharmacyProject.dto.appointment;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyWorkerDTO;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;

public class AppointmentCalendarDTO {
    private Long id;

    private Long start;

    private Long end;

    private AppointmentState appointmentState;

    private String pharmacy;

    private String patient;

    public AppointmentCalendarDTO(){

    }

    public AppointmentCalendarDTO(Appointment appt){
        id = appt.getId();
        start = appt.getStartTime();
        end = appt.getEndTime();
        appointmentState = appt.getAppointmentState();
        pharmacy = appt.getPharmacy().getName();
        patient = appt.getPatient().getFirstName() + " " + appt.getPatient().getLastName();
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

    public AppointmentState getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(AppointmentState appointmentState) {
        this.appointmentState = appointmentState;
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
}
