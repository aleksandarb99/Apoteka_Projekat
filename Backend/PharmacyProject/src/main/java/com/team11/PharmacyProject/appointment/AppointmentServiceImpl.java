package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.appointment.AppointmentPatientInsightDTO;
import com.team11.PharmacyProject.dto.appointment.AppointmentReservationDTO;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PharmacyWorkerRepository pharmacyWorkerRepository;

    @Autowired
    WorkplaceRepository workplaceRepository;

    @Autowired
    PatientRepository patientRepository;

    public Appointment getNextAppointment(String email, Long workerId) {
        Pageable pp = PageRequest.of(0, 1, Sort.by("startTime").ascending());
        List<Appointment> result = appointmentRepository.getUpcommingAppointment(email, workerId, pp);
        if (result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    public List<Appointment> getNextAppointments(String email, Long workerId) {
        Pageable pp = PageRequest.of(0, 6, Sort.by("startTime").ascending());
        List<Appointment> result = appointmentRepository.getUpcommingAppointment(email, workerId, pp);
        return result;
    }

    public List<Appointment> getFreeAppointmentsByPharmacyId(Long id) {
        List<Appointment> appointments = new ArrayList<>();
        Date date = new Date();
        Long currentTime = date.getTime();
        appointmentRepository.findFreeAppointmentsByPharmacyId(id, currentTime).forEach(appointments::add);
        return appointments;
    }

    public List<Appointment> getFreeAppointmentsByPharmacyId(Long id, Sort sorter) {
        List<Appointment> appointments = new ArrayList<>();
        Date date = new Date();
        Long currentTime = date.getTime();
        appointmentRepository.findFreeAppointmentsByPharmacyId(id, currentTime, sorter).forEach(appointments::add);
        return appointments;
    }

    public List<Appointment> getAllAppointmentsByPharmacyId(Long id, Long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<Appointment> appointments = new ArrayList<>();


        Date dateFromRequest = new Date(timestamp);

        for (Appointment a : appointmentRepository.findAllAppointmentsByDermatologistId(id)) {
            Date d = new Date(a.getStartTime());
            if (sdf.format(dateFromRequest).equals(sdf.format(d))) {
                appointments.add(a);
            }
        }
        return appointments;

    }

    public boolean insertAppointment(Appointment a, Long pharmacyId, Long dId) {
        Optional<Pharmacy> p = pharmacyRepository.findById(pharmacyId);
        if (p.isEmpty()) {
            return false;
        }
        Pharmacy pharmacy = p.get();

        PharmacyWorker worker = pharmacyWorkerRepository.findByIdAndFetchAWorkplaces(dId);
        if (worker == null) {
            return false;
        }

        a.setAppointmentState(AppointmentState.EMPTY);
        a.setAppointmentType(AppointmentType.CHECKUP);
        a.setPharmacy(pharmacy);
        a.setWorker(worker);
        a.setEndTime(a.getStartTime() + a.getDuration() * 60000L);

        Date startTime = new Date(a.getStartTime());
        Date endTime = new Date(a.getEndTime());

        Date today = new Date();
        if (startTime.before(today)) {
            return false;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        boolean doesHeWorkThatDay = false;
        for (Workplace wp : workplaceRepository.getWorkplacesByPharmacyIdAndWorkerId(pharmacyId, worker.getId())) {
            for (WorkDay wd : wp.getWorkDays()) {
                if (wd.getWeekday().ordinal() + 1 == dayOfWeek) {
                    doesHeWorkThatDay = true;
                    c.setTime(startTime);   // assigns calendar to given date
                    int hourStart = c.get(Calendar.HOUR_OF_DAY);
                    c.setTime(endTime);   // assigns calendar to given date
                    int hourEnd = c.get(Calendar.HOUR_OF_DAY);

                    if (hourStart < wd.getStartTime()) {
                        return false;
                    }

                    if (hourEnd > wd.getEndTime()) {
                        return false;
                    }
                }
            }

        }

        if(!doesHeWorkThatDay){
            return false;
        }

        for (Appointment appointment : appointmentRepository.findFreeAppointmentsByPharmacyIdAndWorkerId(pharmacyId, worker.getId())) {
            Date d1 = new Date(appointment.getStartTime());
            Date d2 = new Date(appointment.getEndTime());

            if (startTime.compareTo(d1) == 0) {
                return false;
            }

            if (endTime.compareTo(d2) == 0) {
                return false;
            }

            if (startTime.after(d1) && startTime.before(d2)) {
                return false;
            }

            if (endTime.after(d1) && endTime.before(d2)) {
                return false;
            }

            if (d1.after(startTime) && d1.before(endTime)) {
                return false;
            }

            if (d2.after(startTime) && d2.before(endTime)) {
                return false;
            }
        }

        appointmentRepository.save(a);
        return true;
    }

    @Override
    public List<Appointment> getUpcomingAppointmentsForWorker(Long id, int page, int size) {
        Pageable pg = PageRequest.of(page, size, Sort.by("startTime").ascending());
        Long startTime = Instant.now().minus(1, ChronoUnit.HOURS).toEpochMilli();
        return appointmentRepository.getUpcomingAppointmentsForWorker(id, startTime, pg);
    }

    @Override
    public boolean startAppointment(Long id) {
        Optional<Appointment> a = appointmentRepository.findById(id);
        if (a.isPresent()) {
            Appointment appointment = a.get();
            if (appointment.getAppointmentState() != AppointmentState.RESERVED){
                return false;
            }
            Long begin = Instant.now().minus(15, ChronoUnit.MINUTES).toEpochMilli();
            Long end = Instant.now().plus(15, ChronoUnit.MINUTES).toEpochMilli();
            if (appointment.getStartTime() > begin && appointment.getStartTime() < end){
                appointment.setAppointmentState(AppointmentState.IN_PROGRESS);
                appointmentRepository.save(appointment);
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean cancelAppointment(Long id) {
        Slice<Appointment> a = appointmentRepository.getAppointmentByIdFetchPatient(id, PageRequest.of(0, 1));
        if (!a.hasContent()){
            return false;
        }
        Appointment appointment = a.getContent().get(0);
        if (appointment.getAppointmentState() != AppointmentState.RESERVED){
            return false;
        }
        long curr_time = Instant.now().toEpochMilli();
        if (curr_time > appointment.getStartTime()){
            appointment.setAppointmentState(AppointmentState.CANCELLED);
            Patient p = appointment.getPatient();
            p.setPenalties(p.getPenalties()+1);
            appointmentRepository.save(appointment);
            return true;
        }else{
            return false;
        }

    }

    @Override
    public AppointmentReservationDTO reserveCheckupForPatient(Long appId, Long patientId) {

        Patient patient = patientRepository.findByIdAndFetchAppointments(patientId);
        if(patient == null) return null;

        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appId);
        if(appointmentOptional.isEmpty()) return null;

        Appointment appointment = appointmentOptional.get();
        if(appointment.getAppointmentState() != AppointmentState.EMPTY) return null;

        appointment.setAppointmentState(AppointmentState.RESERVED);
        appointment.setPatient(patient);
        patient.addAppointment(appointment);

        patientRepository.save(patient);

        return new AppointmentReservationDTO(patient, appointment);

    }

    @Override
    public AppointmentReservationDTO reserveConsultationForPatient(Long workerId, Long patientId, Long pharmacyId, Long requiredDate) {

        PharmacyWorker worker = pharmacyWorkerRepository.getPharmacyWorkerForCalendar(workerId);
        if (worker == null) return null;

        Patient patient = patientRepository.findByIdAndFetchAppointments(patientId);
        if (patient == null) return null;

        Pharmacy pharmacy = pharmacyRepository.findPharmacyByIdFetchAppointments(pharmacyId);
        if (pharmacy == null) return null;

        Date requestedDateAndTime = new Date(requiredDate);
        Date requestedDateAndTimeEnd = new Date(requiredDate + pharmacy.getConsultationDuration() * 60000L);    // Simulacija trajanja konsultacije
        Date today = new Date();
        if (requestedDateAndTime.before(today)) return null;

        boolean isRequiredConsultationFree = true;
        for (Appointment a : worker.getAppointmentList()) {
            if(!a.getPharmacy().getId().equals(pharmacyId) || (a.getPharmacy().getId().equals(pharmacyId) && a.getAppointmentType() == AppointmentType.CHECKUP))
                continue;
            Date startTime = new Date(a.getStartTime());
            Date endTime = new Date(a.getEndTime());
            if(startTime.compareTo(requestedDateAndTime) == 0) {
                isRequiredConsultationFree = false;
                break;
            }
            if(requestedDateAndTime.after(startTime) && requestedDateAndTime.before(endTime)) {
                isRequiredConsultationFree = false;
                break;
            }
            if(requestedDateAndTimeEnd.after(startTime) && requestedDateAndTimeEnd.before(endTime)) {
                isRequiredConsultationFree = false;
                break;
            }
        }

        if (!isRequiredConsultationFree) return null;

        Appointment reservedConsultation = new Appointment();
        reservedConsultation.setPatient(patient);
        reservedConsultation.setWorker(worker);
        reservedConsultation.setAppointmentState(AppointmentState.RESERVED);
        reservedConsultation.setAppointmentType(AppointmentType.CONSULTATION);
        reservedConsultation.setPharmacy(pharmacy);
        reservedConsultation.setDuration(pharmacy.getConsultationDuration());       // Cupati iz apoteke ako cemo dodati da ima trajanje konsultacije
        reservedConsultation.setStartTime(requiredDate);
        reservedConsultation.setEndTime(requiredDate + pharmacy.getConsultationDuration() * 60000L);
        reservedConsultation.setPrice(pharmacy.getConsultationPrice());

        patient.addAppointment(reservedConsultation);
        worker.addAppointment(reservedConsultation);
        pharmacy.addAppointment(reservedConsultation);

        appointmentRepository.save(reservedConsultation);

        return new AppointmentReservationDTO(reservedConsultation);

    }

    @Override
    public List<AppointmentPatientInsightDTO> getFinishedConsultationsByPatientId(Long id) {

        List<AppointmentPatientInsightDTO> retVal = new ArrayList<>();
        Patient patient = patientRepository.findByIdAndFetchAppointments(id);

        if (patient == null) return retVal;

        for (Appointment a : patient.getAppointments()) {
            if (a.getAppointmentType() == AppointmentType.CHECKUP) continue;
            if (a.getAppointmentState() != AppointmentState.FINISHED) continue;

            Date today = new Date();
            Date startDate = new Date(a.getStartTime()); // Ove 2 naredne provere, ne bi trebale da se dese
            if (startDate.after(today)) continue;

            Date endDate = new Date(a.getEndTime());
            if (endDate.after(today)) continue;

            AppointmentPatientInsightDTO dto = new AppointmentPatientInsightDTO(a);
            retVal.add(dto);
        }

        return retVal;
    }
}
