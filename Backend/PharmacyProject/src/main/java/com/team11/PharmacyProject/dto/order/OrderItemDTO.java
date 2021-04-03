package com.team11.PharmacyProject.dto.order;

import com.team11.PharmacyProject.dto.MedicineDTO;


public class OrderItemDTO {

    private Long id;

    private int amount;

    private MedicineDTO medicine;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long id, int amount, MedicineDTO medicine) {
        this.id = id;
        this.amount = amount;
        this.medicine = medicine;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public MedicineDTO getMedicine() {
        return medicine;
    }

    public void setMedicine(MedicineDTO medicine) {
        this.medicine = medicine;
    }
}
