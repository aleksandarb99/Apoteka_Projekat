package com.team11.PharmacyProject.users.dermatologist;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dermatologistWorkplace.DermatologistWorkplace;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DermatologistService {
    @Autowired
    DermatologistRepository dermatologistRepository;

    public List<Appointment> getDermatologistsUpcommingAppointments(long id){
        return dermatologistRepository.getDermatologistsUpcommingAppointments(id);
    }
}
