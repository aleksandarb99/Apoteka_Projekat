package com.team11.PharmacyProject.dto.rating;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

public class RatingGetEntitiesDTO {

    private long id;
    private String name;
    private double avgGrade;
    private String medicineCode;    // Samo za lek
    private String address; // Samo za apoteku

    public RatingGetEntitiesDTO() {}

    public RatingGetEntitiesDTO(PharmacyWorker worker) {
        setId(worker.getId());
        setName(worker.getFirstName() + " " + worker.getLastName());
        setAvgGrade(worker.getAvgGrade());
        setMedicineCode("");
        setAddress("");
    }

    public RatingGetEntitiesDTO(Medicine medicine) {
        setId(medicine.getId());
        setName(medicine.getName());
        setAvgGrade(medicine.getAvgGrade());
        setMedicineCode(medicine.getCode());
        setAddress("");
    }

    public RatingGetEntitiesDTO(Pharmacy pharmacy) {
        setId(pharmacy.getId());
        setName(pharmacy.getName());
        setAvgGrade(pharmacy.getAvgGrade());
        setMedicineCode("");
        setAddress(pharmacy.getAddress().getStreet() + ", " + pharmacy.getAddress().getCity() + ", " + pharmacy.getAddress().getCountry());
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

    public String getMedicineCode() {
        return medicineCode;
    }

    public void setMedicineCode(String medicineCode) {
        this.medicineCode = medicineCode;
    }
}
