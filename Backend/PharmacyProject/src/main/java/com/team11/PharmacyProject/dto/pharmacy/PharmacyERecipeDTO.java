package com.team11.PharmacyProject.dto.pharmacy;

import com.team11.PharmacyProject.address.Address;

public class PharmacyERecipeDTO {
    private long id;
    private String name;
    private double avgGrade;
    private double totalPrice;
    private Address address;

    public PharmacyERecipeDTO(long id, String name, double avgGrade, double totalPrice, Address address) {
        this.id = id;
        this.name = name;
        this.avgGrade = avgGrade;
        this.totalPrice = totalPrice;
        this.address = address;
    }

    public PharmacyERecipeDTO() {
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
