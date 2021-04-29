package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.appointment.AppointmentPatientInsightDTO;
import com.team11.PharmacyProject.dto.appointment.AppointmentReservationDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface AppointmentService {

    Appointment getNextAppointment(String email, Long workerId);

    List<Appointment> getNextAppointments(String email, Long workerId);

    List<Appointment> getFreeAppointmentsByPharmacyId(Long id);

    List<Appointment> getFreeAppointmentsByPharmacyId(Long id, Sort sorter);

    List<Appointment> getAllAppointmentsByPharmacyId(Long id, Long timestamp);

    boolean insertAppointment(Appointment a, Long pharmacyId, Long dId);

    List<Appointment> getUpcomingAppointmentsForWorker(Long id, int page, int size);

    boolean startAppointment(Long id);

    boolean cancelAppointment(Long id);

    AppointmentReservationDTO reserveCheckupForPatient(Long appId, Long patientId);

    AppointmentReservationDTO reserveConsultationForPatient(Long workerId, Long patientId, Long pharmacyId, Long requiredDate);

    List<AppointmentPatientInsightDTO> getFinishedConsultationsByPatientId(Long id, Sort sorter);

    boolean cancelConsultation(Long id);

    List<AppointmentPatientInsightDTO> getUpcomingConsultationsByPatientId(Long id, Sort sort);
}
