package com.team11.PharmacyProject.dto.user;

import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.user.MyUser;

public class UserViewInfoDTO {
    private long id;
    private String name;

    public UserViewInfoDTO() {
    }

    public UserViewInfoDTO(MyUser myUser) {
        this.id = myUser.getId();
        this.name = String.format("%s %s", myUser.getFirstName(), myUser.getLastName());
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
}
