package com.team11.PharmacyProject.dermatologistWorkplace;

import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.dermatologist.Dermatologist;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;

import java.util.List;

public class DermatologistWorkplace extends Workplace {
    private Dermatologist dermatologist;

    public DermatologistWorkplace(Long id, List<WorkDay> workDays) {
        super(id, workDays);
    }

    public Dermatologist getDermatologist() {
        return dermatologist;
    }

    public void setDermatologist(Dermatologist dermatologist) {
        this.dermatologist = dermatologist;
    }
}