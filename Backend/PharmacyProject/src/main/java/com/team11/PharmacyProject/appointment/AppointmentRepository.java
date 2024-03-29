package com.team11.PharmacyProject.appointment;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT u FROM Appointment u WHERE u.appointmentState = 'EMPTY' AND u.pharmacy.id=?1 AND u.startTime > ?2")
    Iterable<Appointment> findFreeAppointmentsByPharmacyId(Long id, Long currentTime);

    @Query("SELECT u FROM Appointment u WHERE u.appointmentState = 'EMPTY' AND u.pharmacy.id=?1 AND u.startTime > ?2")
    Iterable<Appointment> findFreeAppointmentsByPharmacyId(Long id, Long currentTime, Sort sorter);

    @Query("SELECT u FROM Appointment u WHERE u.pharmacy.id=?1 AND u.worker.id =?2")
    Iterable<Appointment> findFreeAppointmentsByPharmacyIdAndWorkerId(Long id, Long id2);

    @Query("select a from Appointment  a where a.appointmentState = 'RESERVED' and " +
            "a.patient.email = ?1 and a.worker.id = ?2")
    List<Appointment> getUpcommingAppointment(String patEmail, Long workerID, Pageable pp);

    @Query("SELECT u FROM Appointment u WHERE u.worker.id=?1")
    Iterable<Appointment> findAllAppointmentsByDermatologistId(Long id);

    @Query("SELECT a FROM Appointment  a where a.worker.id=?1 and a.appointmentState='RESERVED' and a.startTime > ?2")
    List<Appointment> getUpcomingAppointmentsForWorker(Long id, Long startTime, Pageable pg);

    @Query("SELECT a FROM Appointment a join fetch a.patient where a.id=?1")
    Slice<Appointment> getAppointmentByIdFetchPatient(Long id, Pageable pg);

    @Query("select case when count(a)> 0 then true else false end from Appointment a where " +
            "a.worker.id = ?1 and ((a.startTime >= ?2 and a.startTime <= ?3) or (a.endTime >= ?2 and a.endTime <= ?3))")
    boolean hasAppointmentsInThatDateRange(Long workerID, Long vacationStartTime, Long vacationEndTime);

    @Query("SELECT a FROM Appointment  a where a.worker.id=?1 and a.appointmentState='RESERVED' and a.startTime > ?2 and a.pharmacy.id = ?3")
    List<Appointment> getUpcomingAppointmentsForWorkerByWorkerIdAndPharmacyId(Long id, Long startTime, Long pharmacyId);

    @Query("SELECT a FROM Appointment  a where (a.worker.id=?1 or a.patient.id=?2) and a.startTime > ?3 and a.endTime < ?4 " +
            "order by a.startTime asc")
    List<Appointment> getAppointmentsOfPatientWorkerOnDate(Long workerID, Long patID, Long start, Long end);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Appointment a where (a.worker.id = ?1 or a.patient.id = ?2) and (a.appointmentState='RESERVED' or a.appointmentState = 'EMPTY')")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    List<Appointment> appointmentsOfWorkerAndPatient(Long workerID, Long patientID);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Appointment a where a.worker.id = ?1 and (a.appointmentState='RESERVED' or a.appointmentState='EMPTY')")
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value ="0")})
    List<Appointment> dermAppointments(Long workerID);

    @Query("SELECT a FROM Appointment  a JOIN FETCH a.pharmacy p where a.startTime > ?1 and a.startTime < ?2 and p.id = ?3 and a.appointmentState = 'FINISHED' order by a.startTime asc")
    List<Appointment> getAppointmentBeetwenTwoTimestamps(Long yearAgo, Long currTime, Long pharmacyId);

    @Query("SELECT a FROM Appointment  a where (a.startTime > ?1 and a.appointmentState = 'RESERVED') or " +
            "(a.startTime > ?2 and a.appointmentState='IN_PROGRESS')")
    List<Appointment> getNotFinishedAppointments(Long time, Long time2);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name="javax.persistence.lock.timeout", value = "4000")})
    @Query("SELECT a FROM Appointment  a where a.worker.id=?1")
    List<Appointment> getAppointmentsOfWorker(Long workerId);

    @Query("SELECT  a FROM Appointment  a left join fetch a.therapyPrescriptionList th left join fetch th.medicineReservation mr " +
            " left join fetch mr.medicineItem mi left join fetch mi.medicine m where a.id = ?1")
    Appointment getAppointmentInfo(Long id);
}
