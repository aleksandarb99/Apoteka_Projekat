package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PharmacyWorkerServiceImpl implements  PharmacyWorkerService{

    @Autowired
    PharmacyWorkerRepository pharmacyWorkerRepository;

    @Override
    public PharmacyWorker getWorkerForCalendar(Long id) {
        return pharmacyWorkerRepository.getPharmacyWorkerForCalendar(id);
    }

}
