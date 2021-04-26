package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.appointment.AppointmentRepository;
import com.team11.PharmacyProject.enums.AbsenceType;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
