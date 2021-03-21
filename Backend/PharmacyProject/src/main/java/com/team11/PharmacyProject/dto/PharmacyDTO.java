package com.team11.PharmacyProject.dto;

import javax.validation.constraints.NotBlank;

public class PharmacyDTO {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    // TODO change to Location after implementing maps
    @NotBlank
    private String location;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}