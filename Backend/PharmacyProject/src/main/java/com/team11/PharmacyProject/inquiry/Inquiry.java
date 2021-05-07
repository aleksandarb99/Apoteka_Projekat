package com.team11.PharmacyProject.inquiry;

import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

import javax.persistence.*;

@Entity
public class Inquiry {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_active", nullable = false)
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    private Pharmacy pharmacy;

    @ManyToOne(fetch = FetchType.EAGER)
    private PharmacyWorker worker;

    @ManyToOne(fetch = FetchType.LAZY)
    private MedicineItem medicineItems;

    @Column(name = "date", nullable = false)
    private Long date;

    public Inquiry(Long id, boolean active, Pharmacy pharmacy, PharmacyWorker worker, MedicineItem medicineItems, Long date) {
        this.id = id;
        this.active = active;
        this.pharmacy = pharmacy;
        this.worker = worker;
        this.medicineItems = medicineItems;
        this.date = date;
    }

    public Inquiry() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public PharmacyWorker getWorker() {
        return worker;
    }

    public void setWorker(PharmacyWorker worker) {
        this.worker = worker;
    }

    public MedicineItem getMedicineItems() {
        return medicineItems;
    }

    public void setMedicineItems(MedicineItem medicineItems) {
        this.medicineItems = medicineItems;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }
}
