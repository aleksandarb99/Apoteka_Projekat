package com.team11.PharmacyProject.appointment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

//    TODO Promeni da se gleda u buducnost i da gleda prazne apontmente
    @Query("SELECT u FROM Appointment u WHERE u.appointmentState = 'RESERVED' AND u.pharmacy.id=?1 AND u.startTime < ?2")
    Iterable<Appointment> findFreeAppointmentsByPharmacyId(Long id, Long currentTime);
}
