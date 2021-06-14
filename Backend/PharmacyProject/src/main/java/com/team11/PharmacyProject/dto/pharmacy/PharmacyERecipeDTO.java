package com.team11.PharmacyProject.dto.pharmacy;

public class PharmacyERecipeDTO {
    private long id;
    private String name;
    private Double avgGrade;
    private Double totalPrice;
    private String addressCity;
    private String addressStreet;

    public PharmacyERecipeDTO(long id, String name, Double avgGrade, Double totalPrice, String addressCity, String addressStreet) {
        this.id = id;
        this.name = name;
        this.avgGrade = avgGrade;
        this.totalPrice = totalPrice;
        this.addressCity = addressCity;
        this.addressStreet = addressStreet;
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

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    public String getAddressStreet() {
        return addressStreet;
    }

    public void setAddressStreet(String addressStreet) {
        this.addressStreet = addressStreet;
    }
}
