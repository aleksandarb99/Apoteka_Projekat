package com.team11.PharmacyProject.appointment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT u FROM Appointment u WHERE u.appointmentState = 'EMPTY' AND u.pharmacy.id=?1 AND u.startTime > ?2")
    Iterable<Appointment> findFreeAppointmentsByPharmacyId(Long id, Long currentTime);

    @Query("SELECT u FROM Appointment u WHERE u.pharmacy.id=?1 AND u.worker.id =?2")
    Iterable<Appointment> findFreeAppointmentsByPharmacyIdAndWorkerId(Long id, Long id2);

    @Query("select a from Appointment  a where a.appointmentState = 'RESERVED' and " +
            "a.patient.email = ?1 and a.worker.id = ?2")
    List<Appointment> getUpcommingAppointment(String patEmail, Long workerID, Pageable pp);

    @Query("SELECT u FROM Appointment u WHERE u.worker.id=?1")
    Iterable<Appointment> findAllAppointmentsByDermatologistId(Long id);
}
