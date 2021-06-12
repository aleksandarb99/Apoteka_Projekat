package com.team11.PharmacyProject.medicineFeatures.medicinePrice;

import com.team11.PharmacyProject.advertisement.Advertisement;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.List;

@Entity
public class MedicinePrice implements Comparable<MedicinePrice>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "start_date", nullable = false)
    private Long startDate;

    public MedicinePrice() {
    }

    public MedicinePrice(double price, Long startDate, List<Advertisement> advertisements) {
        this.price = price;
        this.startDate = startDate;
    }

    public MedicinePrice(Long id, double price, Long startDate, List<Advertisement> advertisements) {
        this.id = id;
        this.price = price;
        this.startDate = startDate;
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

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }

    @Override
    public int compareTo(@NotNull MedicinePrice o) {
        return Long.compare(this.id, o.id);
    }
}