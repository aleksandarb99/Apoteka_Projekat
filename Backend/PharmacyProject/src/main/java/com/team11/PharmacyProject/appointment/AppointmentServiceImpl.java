package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.appointment.AppointmentPatientInsightDTO;
import com.team11.PharmacyProject.dto.appointment.AppointmentReservationDTO;
import com.team11.PharmacyProject.dto.therapyPrescription.TherapyPresriptionDTO;
import com.team11.PharmacyProject.dto.worker.WorktimeDTO;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemService;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservation;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.rankingCategory.RankingCategory;
import com.team11.PharmacyProject.rankingCategory.RankingCategoryService;
import com.team11.PharmacyProject.therapyPrescription.TherapyPrescription;
import com.team11.PharmacyProject.requestForHoliday.RequestForHoliday;
import com.team11.PharmacyProject.requestForHoliday.RequestForHolidayService;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceRepository;
import com.team11.PharmacyProject.workplace.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
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

    @Autowired
    MedicineItemService medicineItemService;

    @Autowired
    RequestForHolidayService requestForHolidayService;

    @Autowired
    WorkplaceService workplaceService;

    @Autowired
    RankingCategoryService categoryService;

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

    public List<Appointment> getAllAppointmentsByPharmacyId(Long id, Long timestamp) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        List<Appointment> appointments = new ArrayList<>();


        Date dateFromRequest = new Date(timestamp);
        boolean isHeOnHoliday = requestForHolidayService.isWorkerOnHoliday(id, dateFromRequest);
        if(isHeOnHoliday) throw new Exception("he is on holiday");


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

        if(requestForHolidayService.isWorkerOnHoliday(dId, startTime))
            return false;

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
    public List<Appointment> getUpcomingAppointmentsForWorkerByWorkerIdAndPharmacyId(Long id, Long pharmacyId) {
        Long startTime = Instant.now().minus(1, ChronoUnit.HOURS).toEpochMilli();
        return appointmentRepository.getUpcomingAppointmentsForWorkerByWorkerIdAndPharmacyId(id, startTime, pharmacyId);
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
        if(patient.getPenalties() >= 3) return null;

        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appId);
        if(appointmentOptional.isEmpty()) return null;

        Appointment appointment = appointmentOptional.get();
        if(appointment.getAppointmentState() != AppointmentState.EMPTY) return null;

        appointment.setAppointmentState(AppointmentState.RESERVED);
        appointment.setPatient(patient);

        RankingCategory c = categoryService.getCategoryByPoints(patient.getPoints());   // Ako korisnik pripada nekoj kategoriji, lupi popust
        if (c != null) {
            appointment.setPriceWithDiscout(c.getDiscount());
        }

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
        if (patient.getPenalties() >= 3) return null;

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
            if(a.getPharmacy().getId().equals(pharmacyId) && a.getAppointmentState() == AppointmentState.CANCELLED)
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

        RankingCategory c = categoryService.getCategoryByPoints(patient.getPoints());   // Ako korisnik pripada nekoj kategoriji, lupi popust
        if (c != null) {
            reservedConsultation.setPriceWithDiscout(c.getDiscount());
        }

        patient.addAppointment(reservedConsultation);
        worker.addAppointment(reservedConsultation);
        pharmacy.addAppointment(reservedConsultation);

        appointmentRepository.save(reservedConsultation);

        return new AppointmentReservationDTO(reservedConsultation);

    }

    @Override
    public List<AppointmentPatientInsightDTO> getFinishedConsultationsByPatientId(Long id, Sort sorter) {

        List<AppointmentPatientInsightDTO> retVal = new ArrayList<>();
        Patient patient = patientRepository.findByIdAndFetchAppointments(id);

        if (patient == null) return retVal;

        for (Appointment a : patient.getAppointments()) {
            if (a.getAppointmentType() == AppointmentType.CHECKUP) continue;
            if (a.getAppointmentState() != AppointmentState.FINISHED) continue;
            if (a.getStartTime() > System.currentTimeMillis()) continue; // Ove 2 naredne provere, ne bi trebale da se dese
            if (a.getEndTime() > System.currentTimeMillis()) continue;

            AppointmentPatientInsightDTO dto = new AppointmentPatientInsightDTO(a);
            retVal.add(dto);
        }

        return sortAppointments(retVal, sorter.toString());
    }

    @Override
    public void cancelConsultation(Long id) {

        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if (appointmentOptional.isEmpty()) throw new RuntimeException("The consultation with sent id does not exists!");

        Appointment appointment =appointmentOptional.get();

        if (appointment.getAppointmentState() != AppointmentState.RESERVED) throw new RuntimeException("Only the reserved appointments can be canceled!");
        if (appointment.getAppointmentType() == AppointmentType.CHECKUP) throw new RuntimeException("The consultation with sent id is not consultation!");
        if (appointment.getStartTime() < System.currentTimeMillis()) throw new RuntimeException("You can not cancel the consultation from the past!"); // Provera da ipak nije konsultacija iz proslosti

        long differenceInMinutes = ((appointment.getStartTime() - System.currentTimeMillis()) / (1000 * 60));
        if(differenceInMinutes < 1440) throw new RuntimeException("You can not cancel the consultation less then 24h before it starts!");

        appointment.setAppointmentState(AppointmentState.CANCELLED);

        appointmentRepository.save(appointment);
    }

    @Override
    public void cancelCheckup(Long id) {

        Optional<Appointment> appointmentOptional = appointmentRepository.findById(id);
        if (appointmentOptional.isEmpty()) throw new RuntimeException("The checkup with sent id does not exists!");

        Appointment appointment = appointmentOptional.get();

        if (appointment.getAppointmentState() != AppointmentState.RESERVED) throw new RuntimeException("Only the reserved appointments can be canceled!");
        if (appointment.getAppointmentType() == AppointmentType.CONSULTATION) throw new RuntimeException("The checkup with sent id is not checkup!");

        if (appointment.getStartTime() < System.currentTimeMillis()) throw new RuntimeException("You can not cancel the checkup from the past!"); // Provera da ipak nije pregled iz proslosti

        long differenceInMinutes = ((appointment.getStartTime() - System.currentTimeMillis()) / (1000 * 60));
        if(differenceInMinutes < 1440) throw new RuntimeException("You can not cancel the checkup less then 24h before it starts!");

        appointment.setAppointmentState(AppointmentState.EMPTY);
        Patient patient = patientRepository.findByIdAndFetchAppointments(appointment.getPatient().getId());
        if(!patient.removeAppointment(appointment.getId())) throw new RuntimeException("It was not recorded that this checkup belongs to you!");
        appointment.setPatient(null);

        appointmentRepository.save(appointment);
    }

    @Override
    public List<AppointmentPatientInsightDTO> getUpcomingConsultationsByPatientId(Long id, Sort sorter) {
        List<AppointmentPatientInsightDTO> retVal = new ArrayList<>();
        Patient patient = patientRepository.findByIdAndFetchAppointments(id);

        if (patient == null) return retVal;

        for (Appointment a : patient.getAppointments()) {
            if (a.getAppointmentType() == AppointmentType.CHECKUP) continue;
            if (a.getAppointmentState() != AppointmentState.RESERVED) continue;
            if (a.getStartTime() < System.currentTimeMillis()) continue; // Ove 2 naredne provere, ne bi trebale da se dese
            if (a.getEndTime() < System.currentTimeMillis()) continue;

            AppointmentPatientInsightDTO dto = new AppointmentPatientInsightDTO(a);
            retVal.add(dto);
        }

        return sortAppointments(retVal, sorter.toString());
    }

    @Override
    public List<AppointmentPatientInsightDTO> getFinishedCheckupsByPatientId(Long id, Sort sorter) {
        List<AppointmentPatientInsightDTO> retVal = new ArrayList<>();
        Patient patient = patientRepository.findByIdAndFetchAppointments(id);

        if (patient == null) return retVal;

        for (Appointment a : patient.getAppointments()) {
            if (a.getAppointmentType() == AppointmentType.CONSULTATION) continue;
            if (a.getAppointmentState() != AppointmentState.FINISHED) continue;
            if (a.getStartTime() > System.currentTimeMillis()) continue; // Ove 2 naredne provere, ne bi trebale da se dese
            if (a.getEndTime() > System.currentTimeMillis()) continue;

            AppointmentPatientInsightDTO dto = new AppointmentPatientInsightDTO(a);
            retVal.add(dto);
        }

        return sortAppointments(retVal, sorter.toString());
    }

    @Override
    public List<AppointmentPatientInsightDTO> getUpcomingCheckupsByPatientId(Long id, Sort sorter) {
        List<AppointmentPatientInsightDTO> retVal = new ArrayList<>();
        Patient patient = patientRepository.findByIdAndFetchAppointments(id);

        if (patient == null) return retVal;

        for (Appointment a : patient.getAppointments()) {
            if (a.getAppointmentType() == AppointmentType.CONSULTATION) continue;
            if (a.getAppointmentState() != AppointmentState.RESERVED) continue;
            if (a.getStartTime() < System.currentTimeMillis()) continue; // Ove 2 naredne provere, ne bi trebale da se dese
            if (a.getEndTime() < System.currentTimeMillis()) continue;

            AppointmentPatientInsightDTO dto = new AppointmentPatientInsightDTO(a);
            retVal.add(dto);
        }

        return sortAppointments(retVal, sorter.toString());
    }

    private List<AppointmentPatientInsightDTO> sortAppointments(List<AppointmentPatientInsightDTO> appointments, String sorter) {

        switch (sorter) {
            case "priceasc: ASC":
                appointments = appointments.stream().sorted(Comparator.comparingDouble(AppointmentPatientInsightDTO::getPrice)).collect(Collectors.toList());
                break;
            case "pricedesc: ASC":
                appointments = appointments.stream().sorted(Comparator.comparingDouble(AppointmentPatientInsightDTO::getPrice).reversed()).collect(Collectors.toList());
                break;
            case "durationasc: ASC":
                appointments = appointments.stream().sorted(Comparator.comparingInt(AppointmentPatientInsightDTO::getDuration)).collect(Collectors.toList());
                break;
            case "durationdesc: ASC":
                appointments = appointments.stream().sorted(Comparator.comparingInt(AppointmentPatientInsightDTO::getDuration).reversed()).collect(Collectors.toList());
                break;
            case "start timeasc: ASC":
                appointments = appointments.stream().sorted(Comparator.comparingLong(AppointmentPatientInsightDTO::getStartTime)).collect(Collectors.toList());
                break;
            case "start timedesc: ASC":
                appointments = appointments.stream().sorted(Comparator.comparingLong(AppointmentPatientInsightDTO::getStartTime).reversed()).collect(Collectors.toList());
                break;
            case "end timeasc: ASC":
                appointments = appointments.stream().sorted(Comparator.comparingLong(AppointmentPatientInsightDTO::getEndTime)).collect(Collectors.toList());
                break;
            case "end timedesc: ASC":
                appointments = appointments.stream().sorted(Comparator.comparingLong(AppointmentPatientInsightDTO::getEndTime).reversed()).collect(Collectors.toList());
                break;
        }

        return appointments;
    }

    @Override
    public int calculateProfit(long start, long end, long pharmacyId) {
        int sum = 0;
        List<Appointment> list = appointmentRepository.getAppointmentBeetwenTwoTimestamps(start, end, pharmacyId);
        for (Appointment a:list) {
            sum += a.getPrice();
        }
        return sum;
    }

    @Override
    public Map<String, Integer> getInfoForReport(String period, Long pharmacyId) {
        String[] monthNames = {"January", "February", "March", "April", "May",
                "June", "July", "August", "September", "October", "November", "December"};

        Map<String, Integer> data = new LinkedHashMap<>();

        List<Appointment> list;

        Calendar calendar = Calendar.getInstance();

        long currTime = Instant.now().toEpochMilli();

        calendar.setTime(new Date(currTime));
        calendar.set(calendar.get(Calendar.YEAR)-1, calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        long yearAgo = calendar.getTimeInMillis();

        if(period.equals("Monthly")){
            int count = 0;
            for(int i = calendar.get(Calendar.MONTH) + 1; i<12; i++) {
                String key = monthNames[i]+"-"+calendar.get(Calendar.YEAR);
                data.put(key, 0);
                count++;
            }
            for(int i = 0; i<12-count; i++) {
                String key = monthNames[i]+"-"+(calendar.get(Calendar.YEAR)+1);
                data.put(key, 0);
            }

            list = appointmentRepository.getAppointmentBeetwenTwoTimestamps(yearAgo, currTime, pharmacyId);

            for (Appointment a:list) {
                calendar.setTime(new Date(a.getStartTime()));
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                String key = monthNames[month]+"-"+year;
                if(data.containsKey(key))
                    data.put(key, data.get(key) + 1);
            }
        }
        else if(period.equals("Quarterly")) {
            if(calendar.get(Calendar.MONTH)<=2) {
                data.put(2+"-"+calendar.get(Calendar.YEAR),0);
                data.put(3+"-"+calendar.get(Calendar.YEAR),0);
                data.put(4+"-"+calendar.get(Calendar.YEAR),0);
                data.put(1+"-"+(calendar.get(Calendar.YEAR)+1),0);
            } else if(calendar.get(Calendar.MONTH)<=5) {
                data.put(3+"-"+calendar.get(Calendar.YEAR),0);
                data.put(4+"-"+calendar.get(Calendar.YEAR),0);
                data.put(1+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(2+"-"+(calendar.get(Calendar.YEAR)+1),0);
            } else if(calendar.get(Calendar.MONTH)<=8) {
                data.put(4+"-"+calendar.get(Calendar.YEAR),0);
                data.put(1+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(2+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(3+"-"+(calendar.get(Calendar.YEAR)+1),0);
            } else {
                data.put(1+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(2+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(3+"-"+(calendar.get(Calendar.YEAR)+1),0);
                data.put(4+"-"+(calendar.get(Calendar.YEAR)+1),0);
            }


            calendar.setTime(new Date(currTime));

            if(calendar.get(Calendar.MONTH)<=2) {
                calendar.setTime(new Date(yearAgo));
                calendar.set(calendar.get(Calendar.YEAR), Calendar.APRIL, 1, 0, 0, 0);
                yearAgo = calendar.getTimeInMillis();
            } else if(calendar.get(Calendar.MONTH)<=5) {
                calendar.setTime(new Date(yearAgo));
                calendar.set(calendar.get(Calendar.YEAR), Calendar.JULY, 1, 0, 0, 0);
                yearAgo = calendar.getTimeInMillis();
            } else if(calendar.get(Calendar.MONTH)<=8) {
                calendar.setTime(new Date(yearAgo));
                calendar.set(calendar.get(Calendar.YEAR), Calendar.OCTOBER, 1, 0, 0, 0);
                yearAgo = calendar.getTimeInMillis();
            } else {
                calendar.setTime(new Date(yearAgo));
                calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1, 0, 0, 0);
                yearAgo = calendar.getTimeInMillis();
            }

            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
            yearAgo = calendar.getTimeInMillis();

            list = appointmentRepository.getAppointmentBeetwenTwoTimestamps(yearAgo, currTime, pharmacyId);

            for (Appointment a:list) {
                calendar.setTime(new Date(a.getStartTime()));
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                int quarter;

                if(month<=2) {
                    quarter = 1;
                } else if(month<=5) {
                    quarter = 2;
                } else if(month<=8) {
                    quarter = 3;
                } else {
                    quarter = 4;
                }

                String key = quarter+"-"+year;
                if(data.containsKey(key))
                    data.put(key, data.get(key) + 1);
            }
        } else {
            calendar.setTime(new Date(currTime));
            calendar.set(calendar.get(Calendar.YEAR)-9, Calendar.JANUARY, 1, 0, 0, 0);
            yearAgo = calendar.getTimeInMillis();

            data.put(calendar.get(Calendar.YEAR)+"",0);
            data.put((calendar.get(Calendar.YEAR)+1)+"",0);
            data.put((calendar.get(Calendar.YEAR)+2)+"",0);
            data.put((calendar.get(Calendar.YEAR)+3)+"",0);
            data.put((calendar.get(Calendar.YEAR)+4)+"",0);
            data.put((calendar.get(Calendar.YEAR)+5)+"",0);
            data.put((calendar.get(Calendar.YEAR)+6)+"",0);
            data.put((calendar.get(Calendar.YEAR)+7)+"",0);
            data.put((calendar.get(Calendar.YEAR)+8)+"",0);
            data.put((calendar.get(Calendar.YEAR)+9)+"",0);

            list = appointmentRepository.getAppointmentBeetwenTwoTimestamps(yearAgo, currTime, pharmacyId);

            for (Appointment a:list) {
                calendar.setTime(new Date(a.getStartTime()));
                int year = calendar.get(Calendar.YEAR);
                String key = year+"";
                if(data.containsKey(key))  data.put(key, data.get(key) + 1);
            }
        }
        return data;
    }

    @Override
    public boolean finalizeAppointment(Long appt_id, List<TherapyPresriptionDTO> therapyDTOList, String info){
        Optional<Appointment> appt = appointmentRepository.findById(appt_id);
        Appointment appointment = appt.orElse(null);
        if (appointment == null){
            return false;
        }
        appointment.setInfo(info);

        List<TherapyPrescription> therapyPrescriptionList = new ArrayList<>();

        List<MedicineItem> medicineItemsOfTherapy = new ArrayList<>();

        for (TherapyPresriptionDTO tpDTO : therapyDTOList){
            medicineItemsOfTherapy.add(medicineItemService.findById(tpDTO.getMedicineItemID()));
        }

        for (int i = 0; i < therapyDTOList.size(); i++){
            //dajemo nedelju dana da pacijent pokupi rezervaciju terapije
            Long pickupDate = Instant.now().plus(7, ChronoUnit.DAYS).toEpochMilli();
            Long resDate = Instant.now().toEpochMilli();
            String resID = UUID.randomUUID().toString();
            ReservationState state = ReservationState.RESERVED;
            MedicineItem mi = medicineItemsOfTherapy.get(i);
            Pharmacy pharmacy = appointment.getPharmacy();
            if(!mi.setAmountLessOne()) return false; //smanjujemo kolicinu za 1, todo kod transakcija ovo ce biti zabavno

            MedicineReservation medicineReservation = new MedicineReservation(pickupDate, resDate, resID, state, mi, pharmacy, 0);

            Optional<Patient> patient = Optional.ofNullable(patientRepository.findByIdAndFetchReservationsEagerly(appointment.getPatient().getId()));
            if(patient.isEmpty()) {  //todo videti sa Jovanom sta ovo tacno radi
                patient = patientRepository.findById(appointment.getPatient().getId());
                if(patient.isEmpty()) return false;
                patient.get().setMedicineReservation(new ArrayList<>());
            }
            patient.get().getMedicineReservation().add(medicineReservation);
            //todo notif za pacijenta
            therapyPrescriptionList.add(new TherapyPrescription(medicineReservation, therapyDTOList.get(i).getDuration()));
        }
        appointment.setTherapyPrescriptionList(therapyPrescriptionList);
        appointment.setAppointmentState(AppointmentState.FINISHED);
        appointmentRepository.save(appointment);
        return true;
    }

    public Appointment getAppointmentForReport(Long appointmentID){
        //todo pazi ovde zezne ako se stavi lazy loading
        Optional<Appointment> appt = appointmentRepository.findById(appointmentID);
        Appointment appointment = appt.orElse(null);
        if (appointment == null){
            return null;
        }
        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentsOfPatientWorkerOnDate(Long workerID, Long patID, Long date){
        Long endDate = Instant.ofEpochMilli(date).plus(1, ChronoUnit.DAYS).toEpochMilli();
        return appointmentRepository.getAppointmentsOfPatientWorkerOnDate(workerID, patID, date, endDate);
    }

    @Override
    public Appointment scheduleAppointmentInRange(Long workerID, Long patientID, Long pharmID, Long apptStart,
                                                  Long apptEnd, double price, int duration) throws Exception{
        // provera da li postji radnik uopste
        Optional<PharmacyWorker> worker = pharmacyWorkerRepository.findById(workerID);
        if (worker.isEmpty()) throw new Exception("Invalid worker");
        PharmacyWorker pw = worker.get();

        //kog je tipa - farmaceut ili derm
        boolean isPharmacist = pw.getUserType() == UserType.PHARMACIST;

        //da li je validno vreme pocetka
        if (apptStart < Instant.now().toEpochMilli()){
            throw new Exception("Appointment has to start in future!");
        }

        //da li se preklapaju appointmenti
        boolean taken = appointmentRepository.hasAppointmentsInRange(workerID, patientID, apptStart, apptEnd);
        if (taken){
            throw new Exception("Patient or worker have an appointment in that period!");
        }

        // provera za workplace
        Workplace wp;
        if (isPharmacist)
            wp = workplaceService.getWorkplaceOfPharmacist(workerID);
        else
            wp = workplaceService.getWorkplaceOfDermatologist(workerID, pharmID);

        if (wp == null){
            throw new Exception("Invalid worker/workplace!");
        }

        //da li se vreme sastanka poklapa sa radnim danom
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(apptStart);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1; //-1 zbog enuma
        int starHour = 0; int endHour = 0;
        for (WorkDay workDay : wp.getWorkDays()){
            if (workDay.getWeekday().ordinal() == dayOfWeek){
                starHour = workDay.getStartTime();
                endHour = workDay.getEndTime();
                break;
            }
        }
        if (starHour == endHour){
            throw new Exception("Invalid day of week (worker not working that day)!");
        }

        //OVO -2 JE OFFSET zbog zonske razlike, ovaj truncutated to je malo glup
        // da li se poklapa radno vreme
        long workerStartTimeOnDate = Instant.ofEpochMilli(apptStart).truncatedTo(ChronoUnit.DAYS)
                .plus(starHour-2, ChronoUnit.HOURS).toEpochMilli();
        long workerEndTimeOnDate = Instant.ofEpochMilli(apptStart).truncatedTo(ChronoUnit.DAYS)
                .plus(endHour-2, ChronoUnit.HOURS).toEpochMilli();
        if (!(apptStart >= workerStartTimeOnDate && apptEnd <= workerEndTimeOnDate)){
            throw new Exception("Invalid appointment time (worker not working in that time period)!");
        }

        //da li se preklapa sa odmorom
        if (requestForHolidayService.hasVacationInThatDateRange(workerID, apptStart, apptEnd)){
            throw new Exception("Worker has a vacation in that period!");
        }

        Optional<Patient> patient = patientRepository.findById(patientID);
        if (patient.isEmpty()) throw new Exception("Invalid patient");
        Patient pat = patient.get();

        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(pharmID);
        if (pharmacy.isEmpty()) throw new Exception("Invalid pharmacy");
        Pharmacy pharm = pharmacy.get();

        Appointment cosultation = new Appointment();
        cosultation.setPatient(pat);
        cosultation.setWorker(pw);
        cosultation.setAppointmentState(AppointmentState.RESERVED);
        cosultation.setAppointmentType(AppointmentType.CONSULTATION);
        cosultation.setPharmacy(pharm);
        cosultation.setDuration(duration);
        cosultation.setStartTime(apptStart);
        cosultation.setEndTime(apptEnd);

        if (!isPharmacist) {
            cosultation.setPrice(price);
        }else{
            cosultation.setPrice(pharm.getConsultationPrice());
        }

        appointmentRepository.save(cosultation);

        return cosultation;
    }
}
