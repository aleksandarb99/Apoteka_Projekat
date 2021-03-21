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

    public boolean insertPharmacy(Pharmacy pharmacy) {
        if (pharmacy != null) {
            pharmacyRepository.save(pharmacy);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(long id) {
        if (pharmacyRepository.getPharmacyById(id) != null) {
            pharmacyRepository.delete(id);
            return true;
        } else {
            return false;
        }
    }
}
