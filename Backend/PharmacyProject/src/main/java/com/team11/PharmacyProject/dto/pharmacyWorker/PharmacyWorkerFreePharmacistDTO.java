package com.team11.PharmacyProject.dto.pharmacyWorker;

import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

public class PharmacyWorkerFreePharmacistDTO {

    private long id;

    private double avgGrade;

    private String name;

    public PharmacyWorkerFreePharmacistDTO() {}

    public PharmacyWorkerFreePharmacistDTO(PharmacyWorker worker) {
        setId(worker.getId());
        setAvgGrade(worker.getAvgGrade());
        setName(worker.getFirstName() + " " + worker.getLastName());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
