package com.team11.PharmacyProject.dto.medicineReservation;

import javax.validation.constraints.Min;

public class MedicineReservationInsertDTO {

    private long pickupDate;
    @Min(1)
    private long medicineId;
    @Min(1)
    private long pharmacyId;
    @Min(1)
    private long userId;
    @Min(0)
    private double price;

    public MedicineReservationInsertDTO() {

    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
