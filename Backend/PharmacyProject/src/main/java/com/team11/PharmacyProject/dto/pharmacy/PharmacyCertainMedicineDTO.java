package com.team11.PharmacyProject.dto.pharmacy;

import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import java.util.Collections;
import java.util.List;

public class PharmacyCertainMedicineDTO {

    private Long id;

    private String name;

    private String address;

    private double price;

    public PharmacyCertainMedicineDTO() {
    }

    public PharmacyCertainMedicineDTO(Pharmacy pharmacy) {
        this.id = pharmacy.getId();
        this.name = pharmacy.getName();
        this.address = pharmacy.getAddress().getStreet() + ", " + pharmacy.getAddress().getCity() + ", " + pharmacy.getAddress().getCountry();
        setPrice(pharmacy.getPriceList().getMedicineItems().get(0).getMedicinePrices());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPrice(List<MedicinePrice> prices) {

        if (prices.size() == 0) {
            this.price = 0;
            return;
        }

        if (prices.size() > 1) {
            Collections.sort(prices);
            Collections.reverse(prices);
            this.price = prices.get(0).getPrice();
            return;
        }

        this.price = prices.get(0).getPrice();
    }

    public void setPriceWithDiscout(double discountPercent) {
        this.price = this.price * (100 - discountPercent) / 100;
    }
}
