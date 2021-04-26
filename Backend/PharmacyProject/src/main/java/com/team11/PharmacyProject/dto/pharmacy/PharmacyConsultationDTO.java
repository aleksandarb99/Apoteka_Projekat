package com.team11.PharmacyProject.dto.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import javax.validation.constraints.NotBlank;

public class PharmacyConsultationDTO {

    private long id;
    @NotBlank
    private String name;

    private Double avgGrade;

    private Double consultationPrice;
    @NotBlank
    private String address;

    public PharmacyConsultationDTO() {
    }

    public PharmacyConsultationDTO(Pharmacy pharmacy) {
        setId(pharmacy.getId());
        setName(pharmacy.getName());
        setAvgGrade(pharmacy.getAvgGrade());
        setConsultationPrice(pharmacy.getConsultationPrice());
        setAddress(pharmacy.getAddress());
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

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public Double getConsultationPrice() {
        return consultationPrice;
    }

    public void setConsultationPrice(Double consultationPrice) {
        this.consultationPrice = consultationPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAddress(Address address) {
        this.address = address.getStreet() + ", " + address.getCity() + ", " + address.getCountry();
    }
}
