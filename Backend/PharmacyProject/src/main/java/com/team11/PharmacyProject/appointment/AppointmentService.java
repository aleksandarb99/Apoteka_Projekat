package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    public List<Appointment> getFreeAppointmentsByPharmacyId(Long id) {
        List<Appointment> appointments = new ArrayList<>();
        Date date = new Date();
        Long currentTime = date.getTime();
        appointmentRepository.findFreeAppointmentsByPharmacyId(id, currentTime).forEach(appointments::add);
        return appointments;

    }
}
