package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.users.patient.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    public Appointment getNextAppointment(Long patientId, Long workerId){
        Pageable onlyFirst = (Pageable) PageRequest.of(0, 1);
        return appointmentRepository.getUpcommingAppointment(patientId, workerId, onlyFirst).get(0);
    }
}
