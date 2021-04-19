package com.team11.PharmacyProject.appointment;

import java.util.List;

public interface AppointmentService {

    Appointment getNextAppointment(String email, Long workerId);

    List<Appointment> getNextAppointments(String email, Long workerId);

    List<Appointment> getFreeAppointmentsByPharmacyId(Long id);

    List<Appointment> getAllAppointmentsByPharmacyId(Long id, Long timestamp);

    boolean insertAppointment(Appointment a, Long pharmacyId, Long dId);

    List<Appointment> getUpcomingAppointmentsForWorker(Long id, int page, int size);

    boolean startAppointment(Long id);

    boolean cancelAppointment(Long id);
}
