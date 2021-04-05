package com.team11.PharmacyProject.dto.medicine;


public class MedicineItemDTO {

    public MedicineDTO medicine;
    private Long id;
    private double price;

    public MedicineItemDTO() {
    }

    public MedicineItemDTO(Long id, double price, MedicineDTO medicine) {
        this.id = id;
        this.price = price;
        this.medicine = medicine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public MedicineDTO getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineDTO medicine) {
        this.medicine = medicine;
    }
}
