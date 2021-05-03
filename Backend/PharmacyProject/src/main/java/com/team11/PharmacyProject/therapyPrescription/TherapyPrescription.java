package com.team11.PharmacyProject.therapyPrescription;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;

import javax.persistence.*;

@Entity
public class TherapyPrescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_item_id")
    public MedicineItem medicineItem;

    @Column(name = "therapy_length", nullable = false)
    private int duration;

    @Column(name = "start_time", nullable = false)
    private Long startTime;  // na ovaj datum je napravljena terapija, i placeni lekovi

    @Column(name = "price", nullable = false)
    private double price; // cena na dan kreiranja terapije - lakse zbog izvestaja mozda

    public TherapyPrescription() {
    }

    public TherapyPrescription(MedicineItem medicineItem, int duration, Long startTime, double price) {
        this.medicineItem = medicineItem;
        this.duration = duration;
        this.startTime = startTime;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedicineItem getMedicineItem() {
        return medicineItem;
    }

    public void setMedicineItem(MedicineItem medicineItem) {
        this.medicineItem = medicineItem;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
