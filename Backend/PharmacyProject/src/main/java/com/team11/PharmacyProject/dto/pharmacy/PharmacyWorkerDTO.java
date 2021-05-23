package com.team11.PharmacyProject.dto.pharmacy;

import com.team11.PharmacyProject.dto.user.RoleDTO;
import com.team11.PharmacyProject.users.user.Role;

public class PharmacyWorkerDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private double avgGrade;

    private String email;

    private String telephone;

    private RoleDTO role;

    public PharmacyWorkerDTO() {
    }

    public PharmacyWorkerDTO(Long id, String firstName, String lastName, double avgGrade, String email, String telephone, RoleDTO role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avgGrade = avgGrade;
        this.email = email;
        this.telephone = telephone;
        this.role = role;
    }

    public RoleDTO getUserType() {
        return role;
    }

    public void setUserType(RoleDTO role) {
        this.role = role;
    }

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
