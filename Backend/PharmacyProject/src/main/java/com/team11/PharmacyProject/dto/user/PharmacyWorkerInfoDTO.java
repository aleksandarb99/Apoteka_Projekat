package com.team11.PharmacyProject.dto.user;

import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

public class PharmacyWorkerInfoDTO {
    private long id;
    private String name;

    public PharmacyWorkerInfoDTO() {
    }

    public PharmacyWorkerInfoDTO(PharmacyWorker pw) {
        this.id = pw.getId();
        this.name = String.format("%s %s", pw.getFirstName(), pw.getLastName());
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
