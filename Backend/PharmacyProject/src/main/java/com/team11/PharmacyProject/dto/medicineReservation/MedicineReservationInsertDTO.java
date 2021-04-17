package com.team11.PharmacyProject.dto.medicineReservation;

public class MedicineReservationInsertDTO {

    private long pickupDate;
    private long medicineId;
    private long pharmacyId;
    private long userId;

    public MedicineReservationInsertDTO() {

    }

    public long getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(long pickupDate) {
        this.pickupDate = pickupDate;
    }

    public long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(long medicineId) {
        this.medicineId = medicineId;
    }

    public long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
