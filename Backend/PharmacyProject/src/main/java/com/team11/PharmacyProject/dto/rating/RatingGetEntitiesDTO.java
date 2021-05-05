package com.team11.PharmacyProject.dto.rating;

import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

public class RatingGetEntitiesDTO {

    private long id;
    private String name;
    private double avgGrade;
    private String address; // Samo za apoteku

    public RatingGetEntitiesDTO() {}

    public RatingGetEntitiesDTO(PharmacyWorker worker) {
        setId(worker.getId());
        setName(worker.getFirstName() + " " + worker.getLastName());
        setAvgGrade(worker.getAvgGrade());
        setAddress("");
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
