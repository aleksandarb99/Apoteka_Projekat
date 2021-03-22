package com.team11.PharmacyProject.users.pharmacist;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.pharmacistWorkplace.PharmacistWorkplace;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.workCalendar.WorkCalendar;

import java.util.List;

public class Pharmacist extends PharmacyWorker {
    private PharmacistWorkplace workplace;

    public Pharmacist(Long id, String password, String firstName, String lastName, String email, String telephone,
                      UserType userType, Address address, double avgGrade, WorkCalendar calendar, List<Appointment> list, PharmacistWorkplace workplace) {
        super(id, password, firstName, lastName, email, telephone, userType, address, avgGrade, calendar, list);
        this.workplace = workplace;
    }

    public PharmacistWorkplace getWorkplace() {
        return workplace;
    }

    public void setWorkplace(PharmacistWorkplace workplace) {
        this.workplace = workplace;
    }
}