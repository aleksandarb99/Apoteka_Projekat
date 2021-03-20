package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.enums.Weekday;
import com.team11.PharmacyProject.location.Location;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.users.dermatologist.Dermatologist;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PharmacyService {

    @Autowired
    PharmacyRepository pharmacyRepository;

    public Pharmacy getPharmacyById(Long id) {
        return pharmacyRepository.getPharmacyById(id);
    }

    public List<Pharmacy> getAllPharmacies() {
        Pharmacy p1 = new Pharmacy(1L, "Apoteka Jankovic", "Najbolja apoteka u gradu i samo za vas!", 5.0,
                null, null, null, new Location(44.43, 25.42), null);
        Pharmacy p2 = new Pharmacy(2L, "Zelena Apoteka", "Kod nikoga kao kod nas", 4.0,
                null, null, null, new Location(40.43, 22.42), null);
        Pharmacy p3 = new Pharmacy(3L, "Misa i Glisa", "Najjaci smo bre", 5.0,
                null, null, null, new Location(51.43, 12.42), null);
        Pharmacy p4 = new Pharmacy(4L, "Hola Hola", "Alo Alo", 1.0,
                null, null, null, new Location(25.43, 22.42), null);
        Pharmacy p5 = new Pharmacy(5L, "Crvena Apoteka", "Ponestaje mi ideja", 3.0,
                null, null, null, new Location(33.53, 33.42), null);
        Pharmacy p6 = new Pharmacy(6L, "Apoteka Stamenkovic", "Samo po niskim cenama", 2.0,
                null, null, null, new Location(36.33, 44.42), null);
        Pharmacy p7 = new Pharmacy(7L, "Apoteka Maric", "Ne znam vise", 5.0,
                null, null, null, new Location(67.23, 27.42), null);
        Pharmacy p8 = new Pharmacy(8L, "Kristal", "Odustajem", 4.0,
                null, null, null, new Location(53.13, 30.42), null);
        return List.of(p1, p2, p3, p4, p5, p6, p7, p8);
    }
}
