package com.team11.PharmacyProject.dto.appointment;

import com.team11.PharmacyProject.therapyPrescription.TherapyPrescription;

public class AppointmentTherapyDTO {
    private String code;
    private String medicineName;
    private int therapyLength;

    public AppointmentTherapyDTO(){
    }

    public AppointmentTherapyDTO(TherapyPrescription therapyPrescription){
        therapyLength = therapyPrescription.getDuration();
        code = therapyPrescription.getMedicineReservation().getMedicineItem().getMedicine().getCode();
        medicineName = therapyPrescription.getMedicineReservation().getMedicineItem().getMedicine().getName();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getTherapyLength() {
        return therapyLength;
    }

    public void setTherapyLength(int therapyLength) {
        this.therapyLength = therapyLength;
    }
}
