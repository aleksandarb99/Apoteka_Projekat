package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.appointment.AppointmentRepository;
import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayWithWorkerDetailsDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.AbsenceRequestState;
import com.team11.PharmacyProject.enums.AbsenceType;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestForHolidayServiceImpl implements RequestForHolidayService{

    @Autowired
    RequestForHolidayRepository requestForHolidayRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PharmacyWorkerRepository pharmacyWorkerRepository;

    @Autowired
    WorkplaceService workplaceService;

    @Autowired
    EmailService emailService;

    @Override
    public List<RequestForHoliday> getUnresolvedRequestsByPharmacy(Long pharmacyId) {
        List<Workplace> workplaces = workplaceService.getWorkplacesByPharmacyId(pharmacyId);
        Date now = new Date();

        List<RequestForHoliday> listOfRequests = new ArrayList<>();

        for (Workplace w: workplaces) {
            Long workerId = w.getWorker().getId();
            if(pharmacyId==-1){
                if(w.getWorker().getRole().getName().equals("PHARMACIST"))
                    continue;
            } else {
                if(w.getWorker().getRole().getName().equals("DERMATOLOGIST"))
                    continue;
            }

            List<RequestForHoliday> requests = requestForHolidayRepository.getUnresolvedRequestsByWorker(workerId, now.getTime());
            listOfRequests.addAll(requests);
        }

        return listOfRequests;
    }

    @Override
    public boolean isWorkerOnHoliday(Long workerId, Date date) {
        List<RequestForHoliday> requestForHoliday = requestForHolidayRepository.findOneWithWorkerAndCheckIsHeOnHoliday(workerId, date.getTime());
        return !requestForHoliday.isEmpty();
    }

    @Override
    public void rejectRequest(String requestId, String reason) {
        long id;
        try {
            String substring = requestId.substring(7);
            id = Long.parseLong(substring);
        } catch (Exception e){
            throw new RuntimeException("Cannot parse id!");
        }

        if(reason.equals("")){
            throw new RuntimeException("You must write a reason for rejecting request!");
        }

        RequestForHoliday r = requestForHolidayRepository.findOneWithWorker(id);
        if(r==null)
            throw new RuntimeException("Request with id "+id+" does not exist!");

        r.setDeclineText(reason);
        r.setRequestState(AbsenceRequestState.CANCELLED);
        requestForHolidayRepository.save(r);

        String email = "abuljevic8@gmail.com";
        RequestForHolidayWithWorkerDetailsDTO dto = new RequestForHolidayWithWorkerDetailsDTO(r);

        try {
            emailService.notifyWorkerAboutRequestForHoliday(email, dto, reason);
        } catch (Exception e) {
            throw new RuntimeException("Problem with sending mail!");
        }
    }

    @Override
    public void acceptRequest(String requestId) {
        long id;
        try {
            String substring = requestId.substring(7);
            id = Long.parseLong(substring);
        } catch (Exception e){
            throw new RuntimeException("Cannot parse id!");
        }

        RequestForHoliday r = requestForHolidayRepository.findOneWithWorker(id);

        if(r==null)
            throw new RuntimeException("Request with id "+id+" does not exist!");

        r.setRequestState(AbsenceRequestState.ACCEPTED);
        requestForHolidayRepository.save(r);

        String email = "abuljevic8@gmail.com";
        RequestForHolidayWithWorkerDetailsDTO dto = new RequestForHolidayWithWorkerDetailsDTO(r);

        try {
            emailService.notifyWorkerAboutRequestForHoliday(email, dto, "");
        } catch (Exception e) {
            throw new RuntimeException("Problem with sending mail!");
        }
    }

    @Override
    public String createHolidayRequest(Long workerId, Long start, Long end, AbsenceType absenceType) {
        Optional<PharmacyWorker> worker = pharmacyWorkerRepository.findById(workerId);
        if (worker.isEmpty()){
            return "Error! Invalid worker!";
        }
        boolean hasAppts = appointmentRepository.hasAppointmentsInThatDateRange(workerId, start, end);
        if (hasAppts){  //zauzet je u tom periodu zbog appointmenata
            return "Error! Worker has appointments in that date range!";
        }
        boolean hasVac = requestForHolidayRepository.hasVacationInThatDateRange(workerId, start, end);
        if (hasVac){
            return  "Error! Worker already has pending or accepted vacation in that range!";
        }
        PharmacyWorker pharmacyWorker = worker.get();
        RequestForHoliday holiday = new RequestForHoliday(start, end, absenceType, pharmacyWorker);
        requestForHolidayRepository.save(holiday);
        return "Vacation request successfully sent!";
    }

    @Override
    public List<RequestForHoliday> getWorkerHolidays(Long workerID) {
        return requestForHolidayRepository.getRequestsFromUser(workerID);
    }

    @Override
    public List<RequestForHoliday> getAcceptedWorkerHolidays(Long workerID) {
        return requestForHolidayRepository.getAcceptedRequestsFromUser(workerID);
    }

    @Override
    public List<RequestForHoliday> getRequestForHolidayAcceptedOrPendingInFuture(Long workerID) {
        return requestForHolidayRepository.getRequestForHolidayAcceptedOrPendingInFuture(workerID, Instant.now().toEpochMilli());
    }

    @Override
    public boolean hasVacationInThatDateRange(Long workerID, Long start, Long end){
        return requestForHolidayRepository.hasVacationInThatDateRange(workerID, start, end);
    }

    public void cancelExpiredVacRequests(){
        Long time = Instant.now().toEpochMilli();
        //znaci da je proslo 15 min od pocetka appt
        List<RequestForHoliday> requestForHolidays = requestForHolidayRepository.getExpiredHolidays(time);
        for (RequestForHoliday request : requestForHolidays){
            request.setRequestState(AbsenceRequestState.CANCELLED);
            requestForHolidayRepository.save(request);
        }
    }

}
