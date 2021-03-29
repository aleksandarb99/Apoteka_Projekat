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
    List<Appointment> getUpcommingAppointment(Long patientID, Long workerID, Pageable pg);
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

//    TODO Promeni da se gleda u buducnost i da gleda prazne apontmente
    @Query("SELECT u FROM Appointment u WHERE u.appointmentState = 'RESERVED' AND u.pharmacy.id=?1 AND u.startTime < ?2")
    Iterable<Appointment> findFreeAppointmentsByPharmacyId(Long id, Long currentTime);
}
