package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.enums.Weekday;
import com.team11.PharmacyProject.location.Location;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PharmacyRepository {

    public Pharmacy getPharmacyById(Long id) {
//      TODO Fix this after we create db
        Pharmacy pharmacy = new Pharmacy(1l, "Zelena Apoteka", "Najbolja apoteka u gradu.",
                4.2, new ArrayList<Patient>(), new PriceList(1l, new ArrayList<MedicineItem>()), new ArrayList<Appointment>(),
                new Location(22.22, 11.11), new ArrayList<Workplace>());


        return pharmacy;
    }
}
