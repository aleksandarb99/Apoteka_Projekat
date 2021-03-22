package com.team11.PharmacyProject.users.pharmacist;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.pharmacistWorkplace.PharmacistWorkplace;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.dermatologist.Dermatologist;
import com.team11.PharmacyProject.users.patient.Patient;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PharmacistRepository {
    List<Pharmacist> pharmacistList;

    public PharmacistRepository(){
        this.pharmacistList = new ArrayList<Pharmacist>();
        Pharmacist pharm = new Pharmacist(1L, "djuric", "djura", "djorko", "djole@gmail.com", "092920119",
                UserType.PHARMACIST, null, 3.4, null, new ArrayList<>(),  null);

        pharmacistList.add(pharm);

        Pharmacy pharmacy = new Pharmacy(1L, "Zelena Apoteka", "Najbolja apoteka u gradu.",
                4.2, null, null, null,
                null, null);

        Patient pat = new Patient(1L, "pera", "Pera", "Zdera", "pera@mail.com", "980809890",
                UserType.PATIENT, null, 3, 0, new ArrayList<>(), new ArrayList<>());

        Patient pat2 = new Patient(2L, "pera", "Mika", "Micke", "pera@mail.com", "980809890",
                UserType.PATIENT, null, 3, 0, new ArrayList<>(), new ArrayList<>());

        Patient pat3 = new Patient(1L, "pera", "Djole", "Parice", "pera@mail.com", "980809890",
                UserType.PATIENT, null, 3, 0, new ArrayList<>(), new ArrayList<>());

        Appointment appt2 = new Appointment(2L, LocalDate.now().plusDays(3), null, 69, AppointmentState.RESERVED,
                null, 3000, AppointmentType.CONSULTATION, pat, null, pharmacy);

        Appointment appt3 = new Appointment(3L, LocalDate.now().plusDays(3), null, 69, AppointmentState.RESERVED,
                null, 3000, AppointmentType.CONSULTATION, pat2, null, pharmacy);

        Appointment appt4 = new Appointment(3L, LocalDate.now().plusDays(3), null, 69, AppointmentState.RESERVED,
                null, 3000, AppointmentType.CONSULTATION, pat3, null, pharmacy);

        pharm.getAppointmentList().add(appt4);
        pharm.getAppointmentList().add(appt2);
        pharm.getAppointmentList().add(appt3);

    }

    public List<Appointment> getPharmacistsUpcommingAppointments(long id){
        List<Appointment> appts = new ArrayList<>();
        LocalDate today = LocalDate.now();
        for (Pharmacist pharmacist : pharmacistList) {
            if (pharmacist.getId() == id){
                for (Appointment apt: pharmacist.getAppointmentList()) {
                    if (apt.getStartTime().compareTo(LocalDate.of(today.getYear(), today.getMonth(), today.getDayOfMonth())) >= 0
                    && (apt.getStartTime().compareTo(LocalDate.of(today.getYear(), today.getMonth(), today.plusDays(7).getDayOfMonth()))) < 0){
                        appts.add(apt);
                    }
                }
            }
        }
        return appts;
    }
}
