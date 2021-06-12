package com.team11.PharmacyProject.dto.therapyPrescription;

public class TherapyPresriptionDTO {
    Long medicineItemID;

    int duration;

    public TherapyPresriptionDTO() {
    }

    public Long getMedicineItemID() {
        return medicineItemID;
    }

    public void setMedicineItemID(Long medicineItemID) {
        this.medicineItemID = medicineItemID;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
