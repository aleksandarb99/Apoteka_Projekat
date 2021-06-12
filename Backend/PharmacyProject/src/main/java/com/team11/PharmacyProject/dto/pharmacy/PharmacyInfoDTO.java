package com.team11.PharmacyProject.dto.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

public class PharmacyInfoDTO {
    private long id;
    private String name;
    private Address address;

    public PharmacyInfoDTO() {
    }

    public PharmacyInfoDTO(Pharmacy p) {
        this.id = p.getId();
        this.name = p.getName();
        this.address = p.getAddress();
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
