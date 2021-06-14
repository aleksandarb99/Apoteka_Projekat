package com.team11.PharmacyProject.dto.appointment;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.therapyPrescription.TherapyPrescription;

import java.util.ArrayList;
import java.util.List;

public class AppointmentInfoDTO {
    private String patient;
    private String pharmacy;
    private double price;
    private Long start;
    private Long end;
    private String info;
    private List<AppointmentTherapyDTO> therapyDTOList;

    public AppointmentInfoDTO() {

    }

    public AppointmentInfoDTO(Appointment appointment) {
        patient = appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName();
        pharmacy = appointment.getPharmacy().getName();
        price = appointment.getPrice();
        start = appointment.getStartTime();
        end = appointment.getEndTime();
        info = appointment.getInfo();
        therapyDTOList = new ArrayList<>();
        for (TherapyPrescription therapy : appointment.getTherapyPrescriptionList()) {
            therapyDTOList.add(new AppointmentTherapyDTO(therapy));
        }
    }

    public String getPatient() {
        return patient;
    }

    public void setPatient(String patient) {
        this.patient = patient;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<AppointmentTherapyDTO> getTherapyDTOList() {
        return therapyDTOList;
    }

    public void setTherapyDTOList(List<AppointmentTherapyDTO> therapyDTOList) {
        this.therapyDTOList = therapyDTOList;
    }
}
