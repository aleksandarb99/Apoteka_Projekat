package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PharmacyWorkerServiceImpl implements  PharmacyWorkerService{

    @Autowired
    PharmacyWorkerRepository pharmacyWorkerRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Override
    public PharmacyWorker getWorkerForCalendar(Long id) {
        return pharmacyWorkerRepository.getPharmacyWorkerForCalendar(id);
    }

    @Override
    public List<PharmacyWorker> getFreePharmacistsByPharmacyIdAndDate(Long id, long date, Sort sorter) {

        Pharmacy pharmacy = pharmacyRepository.getPharmacyByIdAndFetchWorkplaces(id);
        if (pharmacy == null) return  null;

        Date requestedDateAndTime = new Date(date);
        Date requestedDateAndTimeEnd = new Date(date);
        Date today = new Date();
        if (requestedDateAndTime.before(today)) return null;

        Calendar c = Calendar.getInstance();
        c.setTime(requestedDateAndTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        List<PharmacyWorker> chosenWorkers = new ArrayList<>();
        for (Workplace wp : pharmacy.getWorkplaces()) {
            if (wp.getWorker().getUserType() != UserType.PHARMACIST) continue;

            for (WorkDay wd : wp.getWorkDays()) {
                if (wd.getWeekday().ordinal() + 1 == dayOfWeek) {
                    int requestedTime = c.get(Calendar.HOUR_OF_DAY);

                    if (requestedTime < wd.getStartTime() || requestedTime > wd.getEndTime()) continue;

                    boolean isPharmacistFree = true;
                    // Kad prebacimo u lazi, prepravi da kad gore dobavim apoteke, fetchujem i workere i njihove appointemnte
                    for (Appointment a : pharmacyWorkerRepository.getPharmacyWorkerForCalendar(wp.getWorker().getId()).getAppointmentList()) {
                        Date startTime = new Date(a.getStartTime());
                        Date endTime = new Date(a.getEndTime());
                        if(startTime.compareTo(requestedDateAndTime) == 0) {
                            isPharmacistFree = false;
                            break;
                        }
                        if(requestedDateAndTime.after(startTime) && requestedDateAndTime.before(endTime)) {
                            isPharmacistFree = false;
                            break;
                        }
                        if(requestedDateAndTimeEnd.after(startTime) && requestedDateAndTimeEnd.before(endTime)) {
                            isPharmacistFree = false;
                            break;
                        }
                    }

                    if(!isPharmacistFree) continue;

                    chosenWorkers.add(wp.getWorker());
                    break;
                }
            }
        }

        if (sorter.toString().equals("UNSORTED"))
            return chosenWorkers;

        if(sorter.toString().equals("avgGrade: DESC"))
            chosenWorkers = chosenWorkers.stream()
                    .sorted(Comparator.comparingDouble(PharmacyWorker::getAvgGrade).reversed())
                    .collect(Collectors.toList());
        else
            chosenWorkers = chosenWorkers.stream()
                    .sorted(Comparator.comparingDouble(PharmacyWorker::getAvgGrade))
                    .collect(Collectors.toList());

        return chosenWorkers;
    }
}
