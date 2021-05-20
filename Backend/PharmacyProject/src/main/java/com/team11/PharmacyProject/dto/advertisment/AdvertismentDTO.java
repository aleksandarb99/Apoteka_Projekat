package com.team11.PharmacyProject.dto.advertisment;

import com.team11.PharmacyProject.dto.medicine.MedicineItemDTO;
import com.team11.PharmacyProject.enums.AdvertisementType;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;

public class AdvertismentDTO {

    private Long id;

    private Long startDate;

    private Long endDate;

    private MedicineItemDTO medicineItem;

    private String advertisementText;

    private AdvertisementType type;

    private double discountPercent;

    public AdvertismentDTO(Long id, Long startDate, Long endDate, MedicineItemDTO medicineItem, String advertisementText, AdvertisementType type, double discountPercent) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.medicineItem = medicineItem;
        this.advertisementText = advertisementText;
        this.type = type;
        this.discountPercent = discountPercent;
    }

    public MedicineItemDTO getMedicineItem() {
        return medicineItem;
    }

    public void setMedicineItem(MedicineItemDTO medicineItem) {
        this.medicineItem = medicineItem;
    }

    public AdvertismentDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getAdvertisementText() {
        return advertisementText;
    }

    public void setAdvertisementText(String advertisementText) {
        this.advertisementText = advertisementText;
    }

    public AdvertisementType getType() {
        return type;
    }

    public void setType(AdvertisementType type) {
        this.type = type;
    }

    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }
}