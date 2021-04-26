package com.team11.PharmacyProject.dto.supplier;

public class SupplierStockItemDTO {
    private long medicineId;
    private String medicineName;
    private double amount;

    public SupplierStockItemDTO() {
    }

    public SupplierStockItemDTO(long medicineId, String medicineName, double totalPrice) {
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
