package com.team11.PharmacyProject.dto.order;

import com.team11.PharmacyProject.dto.medicine.MedicineDTO;

public class OrderItemAddingDTO {

    private int amount;

    private Long medicineId;

    private String name;

    public OrderItemAddingDTO(int amount, Long medicineId, String name) {
        this.amount = amount;
        this.medicineId = medicineId;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OrderItemAddingDTO() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }
}
