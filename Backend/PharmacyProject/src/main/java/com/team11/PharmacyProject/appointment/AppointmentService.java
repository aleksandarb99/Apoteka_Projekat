package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.appointment.AppointmentPatientInsightDTO;
import com.team11.PharmacyProject.dto.appointment.AppointmentReservationDTO;
import com.team11.PharmacyProject.dto.therapyPrescription.TherapyPresriptionDTO;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface AppointmentService {

    Appointment getNextAppointment(String email, Long workerId);

    List<Appointment> getNextAppointments(String email, Long workerId);

    List<Appointment> getFreeAppointmentsByPharmacyId(Long id);

    List<Appointment> getFreeAppointmentsByPharmacyId(Long id, Sort sorter);

    List<Appointment> getAllAppointmentsByPharmacyId(Long id, Long timestamp) throws Exception;

    void insertAppointment(Appointment a, Long pharmacyId, Long dId);

    List<Appointment> getUpcomingAppointmentsForWorker(Long id, int page, int size);

    boolean startAppointment(Long id);

    boolean cancelAppointment(Long id);

    AppointmentReservationDTO reserveCheckupForPatient(Long appId, Long patientId);

    AppointmentReservationDTO reserveConsultationForPatient(Long workerId, Long patientId, Long pharmacyId, Long requiredDate);

    List<AppointmentPatientInsightDTO> getFinishedConsultationsByPatientId(Long id, Sort sorter);

    boolean cancelConsultation(Long id);

    List<AppointmentPatientInsightDTO> getUpcomingConsultationsByPatientId(Long id, Sort sort);

    List<Appointment> getUpcomingAppointmentsForWorkerByWorkerIdAndPharmacyId(Long id, Long pharmacyId);

    List<AppointmentPatientInsightDTO> getFinishedCheckupsByPatientId(Long id, Sort sort);

    boolean cancelCheckup(Long id);

    List<AppointmentPatientInsightDTO> getUpcomingCheckupsByPatientId(Long id, Sort sort);

    boolean finalizeAppointment(Long appt_id, List<TherapyPresriptionDTO> tpDTO, String info);

    Appointment getAppointmentForReport(Long appointmentID);

    List<Appointment> getAppointmentsOfPatientWorkerOnDate(Long workerID, Long patID, Long date);

    Appointment scheduleAppointmentInRange(Long workerID, Long patientID, Long pharmID, Long apptStart, Long apptEnd, double price, int duration) throws Exception;

    Map<String, Integer> getInfoForReport(String period, Long pharmacyId);

    int calculateProfit(long start, long end, long pharmacyId);
}
