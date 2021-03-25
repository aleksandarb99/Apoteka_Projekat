package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.workDay.WorkDay;

import javax.persistence.*;
import java.util.List;

@Entity
public class Workplace  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private PharmacyWorker worker;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "workplace_id")
    private List<WorkDay> workDays;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    public Workplace() {}

    public Workplace(Long id, PharmacyWorker worker, List<WorkDay> workDays, Pharmacy pharmacy) {
        this.id = id;
        this.worker = worker;
        this.workDays = workDays;
        this.pharmacy = pharmacy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PharmacyWorker getWorker() {
        return worker;
    }

    public void setWorker(PharmacyWorker worker) {
        this.worker = worker;
    }

    public List<WorkDay> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<WorkDay> workDays) {
        this.workDays = workDays;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}