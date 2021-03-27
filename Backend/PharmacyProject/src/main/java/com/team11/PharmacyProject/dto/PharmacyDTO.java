package com.team11.PharmacyProject.dto;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.priceList.PriceList;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PharmacyDTO {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private Double avgGrade;
    @NotNull
    private Address address;

    private PriceList priceList;

    public PriceList getPriceList() {
        return priceList;
    }

    public void setPriceList(PriceList priceList) {
        this.priceList = priceList;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

}
