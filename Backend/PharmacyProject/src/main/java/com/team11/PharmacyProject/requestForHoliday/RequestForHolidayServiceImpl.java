package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.appointment.AppointmentRepository;
import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayDTO;
import com.team11.PharmacyProject.dto.requestForHoliday.RequestForHolidayWithWorkerDetailsDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.enums.AbsenceRequestState;
import com.team11.PharmacyProject.enums.AbsenceType;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public boolean rejectRequest(String requestId, String reason) {
        long id;
        try {
            String substring = requestId.substring(7);
            id = Long.parseLong(substring);
        } catch (Exception e){
            return false;
        }

        if(reason.equals("")){
            return false;
        }

        RequestForHoliday r = requestForHolidayRepository.findOneWithWorker(id);
        if(r != null){

            r.setDeclineText(reason);
            r.setRequestState(AbsenceRequestState.CANCELLED);
            requestForHolidayRepository.save(r);

//          String email = r.getPharmacyWorker().getEmail()
            String email = "abuljevic8@gmail.com";
            RequestForHolidayWithWorkerDetailsDTO dto = new RequestForHolidayWithWorkerDetailsDTO(r);

            try {
                emailService.notifyWorkerAboutRequestForHoliday(email, dto, reason);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean acceptRequest(String requestId) {
        long id;
        try {
            String substring = requestId.substring(7);
            id = Long.parseLong(substring);
        } catch (Exception e){
            return false;
        }

        RequestForHoliday r = requestForHolidayRepository.findOneWithWorker(id);
        if(r != null){

            r.setRequestState(AbsenceRequestState.ACCEPTED);
            requestForHolidayRepository.save(r);

//          String email = r.getPharmacyWorker().getEmail()
            String email = "abuljevic8@gmail.com";
            RequestForHolidayWithWorkerDetailsDTO dto = new RequestForHolidayWithWorkerDetailsDTO(r);


            try {
                emailService.notifyWorkerAboutRequestForHoliday(email, dto, "");
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        return false;
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
}
