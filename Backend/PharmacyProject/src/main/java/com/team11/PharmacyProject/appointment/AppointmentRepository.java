package com.team11.PharmacyProject.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

//    TODO Promeni da se gleda u buducnost i da gleda prazne apontmente
    @Query("SELECT u FROM Appointment u WHERE u.appointmentState = 'RESERVED' AND u.pharmacy.id=?1 AND u.startTime < ?2")
    Iterable<Appointment> findFreeAppointmentsByPharmacyId(Long id, Long currentTime);

    @Query("select a from Appointment  a where a.appointmentState = 'RESERVED' and " +
            "a.patient.email = ?1 and a.worker.id = ?2")
    List<Appointment> getUpcommingAppointment(String patEmail, Long workerID, Pageable pp);
}
