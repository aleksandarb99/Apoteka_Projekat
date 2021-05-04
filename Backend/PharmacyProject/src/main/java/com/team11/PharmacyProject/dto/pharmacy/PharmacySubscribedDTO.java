package com.team11.PharmacyProject.dto.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

public class PharmacySubscribedDTO {

    private long id;
    private String name;
    private String description;
    private Double avgGrade;
    private String address;

    public PharmacySubscribedDTO() {
    }

    public PharmacySubscribedDTO(Pharmacy p) {
        this.id = p.getId();
        this.name = p.getName();
        this.description = p.getDescription();
        this.avgGrade = p.getAvgGrade();
        setAddress(p.getAddress());
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(Address address) {

        this.address = address.getStreet() + ", " + address.getCity() + ", " + address.getCountry();
    }

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

}
