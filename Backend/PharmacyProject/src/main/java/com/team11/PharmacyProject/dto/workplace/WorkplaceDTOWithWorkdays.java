package com.team11.PharmacyProject.dto.workplace;

import com.team11.PharmacyProject.dto.pharmacy.PharmacyWorkerDTO;
import com.team11.PharmacyProject.workDay.WorkDay;

import java.util.List;

public class WorkplaceDTOWithWorkdays {
    private Long id;

    private PharmacyWorkerDTO worker;

    private List<WorkDay> workDays;

    public WorkplaceDTOWithWorkdays(Long id, PharmacyWorkerDTO worker, List<WorkDay> workDays) {
        this.id = id;
        this.worker = worker;
        this.workDays = workDays;
    }

    public List<WorkDay> getWorkDays() {
        return workDays;
    }

    public void setWorkDays(List<WorkDay> workDays) {
        this.workDays = workDays;
    }

    public WorkplaceDTOWithWorkdays() {
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

