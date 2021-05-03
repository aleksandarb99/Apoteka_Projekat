package com.team11.PharmacyProject.dto.therapyPrescription;

public class TherapyPresriptionDTO {
    Long medicineItemID;

    int duration;

    boolean isAlternative;

    Long originalMedicineItemID; //id prvobitnog leka; zbog notifikacija

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

    public boolean isAlternative() {
        return isAlternative;
    }

    public void setAlternative(boolean alternative) {
        isAlternative = alternative;
    }

    public Long getOriginalMedicineItemID() {
        return originalMedicineItemID;
    }

    public void setOriginalMedicineItemID(Long originalMedicineItemID) {
        this.originalMedicineItemID = originalMedicineItemID;
    }
}
