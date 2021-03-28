package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.users.patient.Patient;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.team11.PharmacyProject.enums.AppointmentState;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {
    @Query("select a from Appointment  a where a.appointmentState = 'RESERVED' and " +
            "a.patient.id = ?1 and a.worker.id = ?2 order by a.startTime asc ")
    Appointment getUpcommingAppointment(Long patientID, Long workerID, Pageable pg);
}
