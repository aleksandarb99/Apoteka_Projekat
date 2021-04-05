package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dto.appointment.AppointmentCalendarDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/workers")
public class PharmacyWorkerController {

    @Autowired
    PharmacyWorkerService pharmacyWorkerService;

    @GetMapping(value = "/calendarAppointments/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<AppointmentCalendarDTO>> getWorkerCalendar(@PathVariable("id") Long id){
        //todo dodati mozda i radno vreme u kalendar
        PharmacyWorker worker = pharmacyWorkerService.getWorkerForCalendar(id);

        if (worker == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<AppointmentCalendarDTO> appts = new ArrayList<>();
        for (Appointment appointment : worker.getAppointmentList()) {
            appts.add(new AppointmentCalendarDTO(appointment));
        }

        return new ResponseEntity<List<AppointmentCalendarDTO>>(appts, HttpStatus.OK);
    }
}
