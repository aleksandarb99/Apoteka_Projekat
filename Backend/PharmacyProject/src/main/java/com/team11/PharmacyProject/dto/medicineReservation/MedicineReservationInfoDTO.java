package com.team11.PharmacyProject.dto.medicineReservation;

import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;

public class MedicineReservationInfoDTO {

    private Long id;
    private Long pickupDate;
    private Long reservationDate;
    private String reservationID;
    private String medicineName;
    private String pharmacyName;

    public MedicineReservationInfoDTO() {}

    public MedicineReservationInfoDTO(MedicineReservation r) {
        setId(r.getId());
        setPickupDate(r.getPickupDate());
        setReservationDate(r.getReservationDate());
        setReservationID(r.getReservationID());
        setMedicineName(r.getMedicineItem().getMedicine().getName());
        setPharmacyName(r.getPharmacy().getName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Long pickupDate) {
        this.pickupDate = pickupDate;
    }

    public Long getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Long reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }
}
