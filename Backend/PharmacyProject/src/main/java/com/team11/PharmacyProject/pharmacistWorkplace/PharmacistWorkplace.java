package com.team11.PharmacyProject.pharmacistWorkplace;

import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.pharmacist.Pharmacist;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;

import java.util.List;

public class PharmacistWorkplace extends Workplace {
    private Pharmacist pharmacist;

    public PharmacistWorkplace(Long id, List<WorkDay> workDays) {
        super(id, workDays);
    }
}