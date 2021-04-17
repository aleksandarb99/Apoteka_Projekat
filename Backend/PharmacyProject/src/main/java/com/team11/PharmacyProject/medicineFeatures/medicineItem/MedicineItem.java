package com.team11.PharmacyProject.medicineFeatures.medicineItem;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;

import javax.persistence.*;
import java.util.List;

@Entity
public class MedicineItem {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public List<MedicinePrice> medicinePrices;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_id")
    public Medicine medicine;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount", nullable = false)
    private int amount;

    public MedicineItem() {
    }

    public MedicineItem(Long id, int amount, List<MedicinePrice> medicinePrices, Medicine medicine) {
        this.id = id;
        this.amount = amount;
        this.medicinePrices = medicinePrices;
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

    public boolean setAmountLessOne() {
        if( amount <= 0) return false;

        amount -= 1;
        return true;
    }

    public List<MedicinePrice> getMedicinePrices() {
        return medicinePrices;
    }

    public void setMedicinePrices(List<MedicinePrice> medicinePrices) {
        this.medicinePrices = medicinePrices;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}