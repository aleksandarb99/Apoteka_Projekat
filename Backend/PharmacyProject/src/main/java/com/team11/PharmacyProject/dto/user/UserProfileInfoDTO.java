package com.team11.PharmacyProject.dto.user;

import com.team11.PharmacyProject.address.Address;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserProfileInfoDTO {
    private Long id;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String email;

    @NotEmpty
    //@Pattern(regexp="(^$|[0-9]{10})")
    private String telephone;

    @NotNull
    private Address address;

    private String roleName;

    private boolean isPasswordChanged;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isPasswordChanged() {
        return isPasswordChanged;
    }

    public void setPasswordChanged(boolean passwordChanged) {
        isPasswordChanged = passwordChanged;
    }
}
