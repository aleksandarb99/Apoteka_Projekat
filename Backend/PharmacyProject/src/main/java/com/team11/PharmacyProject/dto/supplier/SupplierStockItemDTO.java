package com.team11.PharmacyProject.dto.supplier;

public class SupplierStockItemDTO {
    private long medicineId;
    private String medicineName;
    private int amount;

    public SupplierStockItemDTO() {
    }

    public SupplierStockItemDTO(long medicineId, String medicineName, int totalPrice) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.amount = totalPrice;
    }

    public long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(long medicineId) {
        this.medicineId = medicineId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
