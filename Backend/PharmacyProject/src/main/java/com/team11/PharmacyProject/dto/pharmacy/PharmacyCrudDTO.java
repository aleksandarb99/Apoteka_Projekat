package com.team11.PharmacyProject.dto.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.dto.user.PharmacyWorkerInfoDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PharmacyCrudDTO {
    private long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    private Double avgGrade = 0.0;

    private int pointsForAppointment;

    @NotNull
    private Address address;

    private List<PharmacyWorkerInfoDTO> admins;

    public PharmacyCrudDTO() {
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

    public Double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(Double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


    public int getPointsForAppointment() {
        return pointsForAppointment;
    }

    public void setPointsForAppointment(int pointsForAppointment) {
        this.pointsForAppointment = pointsForAppointment;
    }

    public List<PharmacyWorkerInfoDTO> getAdmins() {
        return admins;
    }

    public void setAdmins(List<PharmacyWorkerInfoDTO> admins) {
        this.admins = admins;
    }
}
