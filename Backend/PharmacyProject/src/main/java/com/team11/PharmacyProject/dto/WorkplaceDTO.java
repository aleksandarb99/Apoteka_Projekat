package com.team11.PharmacyProject.dto;

import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

public class WorkplaceDTO {

    private Long id;

    private PharmacyWorkerDTO worker;

    public WorkplaceDTO(Long id, PharmacyWorkerDTO worker) {
        this.id = id;
        this.worker = worker;
    }

    public WorkplaceDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PharmacyWorkerDTO getWorker() {
        return worker;
    }

    public void setWorker(PharmacyWorkerDTO worker) {
        this.worker = worker;
    }
}
