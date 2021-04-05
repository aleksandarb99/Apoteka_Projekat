package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.myOrder.MyOrder;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PharmacyWorkerRepository pharmacyWorkerRepository;

    @Autowired
    WorkplaceRepository workplaceRepository;

    public Appointment getNextAppointment(String email, Long workerId) {
        Pageable pp = (Pageable) PageRequest.of(0,1, Sort.by("startTime").ascending());
        List<Appointment> result = appointmentRepository.getUpcommingAppointment(email, workerId, pp);
        if (result.isEmpty()){
            return null;
        }
        return result.get(0);
    }

    public List<Appointment> getNextAppointments(String email, Long workerId) {
        Pageable pp = (Pageable) PageRequest.of(0,6, Sort.by("startTime").ascending());
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

    public List<Appointment> getAllAppointmentsByPharmacyId(Long id, Long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<Appointment> appointments = new ArrayList<>();


        Date dateFromRequest = new Date(timestamp);

        for(Appointment a : appointmentRepository.findAllAppointmentsByDermatologistId(id)){
            Date d = new Date(a.getStartTime());
            if(sdf.format(dateFromRequest).equals(sdf.format(d))) {
                appointments.add(a);
            }
        }
        return appointments;

    }

    public boolean insertAppointment(Appointment a, Long pharmacyId, Long dId) {
        Optional<Pharmacy> p = pharmacyRepository.findById(pharmacyId);
        if(p.isEmpty()){
            return false;
        }
        Pharmacy pharmacy = p.get();

        PharmacyWorker worker = pharmacyWorkerRepository.findByIdAndFetchAWorkplaces(dId);
        if(worker == null){
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
        if(startTime.before(today)){
            return false;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(startTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        for (Workplace wp:workplaceRepository.getWorkplacesByPharmacyIdAndWorkerId(pharmacyId, worker.getId())) {
            for (WorkDay wd: wp.getWorkDays()) {
                System.out.println("NAS ENUM");
                System.out.println(wd.getWeekday().ordinal() + 1);
                System.out.println("ONAJ OD KALENDARA");
                System.out.println(dayOfWeek);
                if(wd.getWeekday().ordinal() + 1 == dayOfWeek){

                    c.setTime(startTime);   // assigns calendar to given date
                    int hourStart = c.get(Calendar.HOUR_OF_DAY);
                    c.setTime(endTime);   // assigns calendar to given date
                    int hourEnd = c.get(Calendar.HOUR_OF_DAY);

                    if(hourStart < wd.getStartTime()){
                        return false;
                    }

                    if(hourEnd > wd.getEndTime()){
                        return false;
                    }
                }
            }

        }
        for (Appointment appointment: appointmentRepository.findFreeAppointmentsByPharmacyIdAndWorkerId(pharmacyId, worker.getId())) {
            Date d1 = new Date(appointment.getStartTime());
            Date d2 = new Date(appointment.getEndTime());

            if(startTime.compareTo(d1)==0){
                return false;
            }

            if(endTime.compareTo(d2)==0){
                return false;
            }

            if(startTime.after(d1) && startTime.before(d2)){
                return false;
            }

            if(endTime.after(d1) && endTime.before(d2)){
                return false;
            }

            if(d1.after(startTime) && d1.before(endTime)){
                return false;
            }

            if(d2.after(startTime) && d2.before(endTime)){
                return false;
            }
        }

        appointmentRepository.save(a);
        return true;
    }
}
