package com.team11.PharmacyProject.dto.medicine;


public class MedicineItemDTO {

    public MedicineDTO medicine;
    private Long id;
    private double price;
    private int amount;

    public MedicineItemDTO() {
    }

    public MedicineItemDTO(MedicineDTO medicine, Long id, double price, int amount) {
        this.medicine = medicine;
        this.id = id;
        this.price = price;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
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
