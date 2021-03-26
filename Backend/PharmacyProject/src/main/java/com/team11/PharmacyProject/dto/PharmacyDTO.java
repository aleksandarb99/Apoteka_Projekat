package com.team11.PharmacyProject.dto;

import com.team11.PharmacyProject.address.Address;

import javax.validation.constraints.NotBlank;

public class PharmacyDTO {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    @NotBlank
    private Address address;

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
}
