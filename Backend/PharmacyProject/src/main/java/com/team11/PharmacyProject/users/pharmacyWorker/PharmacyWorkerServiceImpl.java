package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyRepository;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import com.team11.PharmacyProject.workDay.WorkDay;
import com.team11.PharmacyProject.workplace.Workplace;
import com.team11.PharmacyProject.workplace.WorkplaceService;
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
    PatientRepository patientRepository;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    WorkplaceService workplaceService;

    @Override
    public PharmacyWorker getOne(Long id) {
        return pharmacyWorkerRepository.findByIdAndFetchAWorkplaces(id);
    }

    @Override
    public PharmacyWorker getWorkerForCalendar(Long id) {
        return pharmacyWorkerRepository.getPharmacyWorkerForCalendar(id);
    }

    @Override
    public void save(PharmacyWorker worker) {
        pharmacyWorkerRepository.save(worker);
    }

    @Override
    public List<PharmacyWorker> findAll() {
        return pharmacyWorkerRepository.findAll();
    }

    @Override
    public List<PharmacyWorker> getNotWorkingWorkersByPharmacyId(Long pharmacyId, RequestForWorkerDTO dto) {
        List<PharmacyWorker> workers = new ArrayList<>();
        List<PharmacyWorker> allWorkers = pharmacyWorkerRepository.findAllAndFetchAWorkplaces();

        List<Workplace> workplaceList = workplaceService.getWorkplacesByPharmacyId(pharmacyId);

        for (PharmacyWorker worker:
            allWorkers) {
            if(worker.getWorkplaces().size() == 0) {
                workers.add(worker);
                continue;
            }
            if(worker.getRole().getName().equals("DERMATOLOGIST")){
                boolean flag = false;
                boolean heIsFree = true;
                for (Workplace workplace: worker.getWorkplaces()) {
                    for (Workplace workplace2:workplaceList) {
                        if (workplace.getId().equals(workplace2.getId())) {
                            flag = true;
                            break;
                        }
                    }
                    if(flag){
                        heIsFree = false;
                       break;
                    }

                    for (WorkDay day:workplace.getWorkDays()) {
                        boolean flag2 = false;
                        if(day.getWeekday().ordinal() == 0){
                            flag2 = dto.isEnable7();
                        }else if(day.getWeekday().ordinal() == 1){
                            flag2 = dto.isEnable1();
                        }
                        else if(day.getWeekday().ordinal()== 2){
                            flag2 = dto.isEnable2();
                        }
                        else if(day.getWeekday().ordinal()== 3){
                            flag2 = dto.isEnable3();
                        }
                        else if(day.getWeekday().ordinal() == 4){
                            flag2 = dto.isEnable4();
                        }
                        else if(day.getWeekday().ordinal() == 5){
                            flag2 = dto.isEnable5();
                        }
                        else if(day.getWeekday().ordinal() == 6){
                            flag2 = dto.isEnable6();
                        }

                        if(flag2){
                            if(dto.getEndHour() < day.getStartTime()){
                                continue;
                            }
                            else if(dto.getStartHour() > day.getEndTime()){
                                continue;
                            }else {
                                heIsFree = false;
                                break;
                            }
                        }

                    }

                }

                if(heIsFree){
                    workers.add(worker);
                }

            }

        }
        return workers;
    }

    @Override
    public List<PharmacyWorker> getFreePharmacistsByPharmacyIdAndDate(Long id, long date, Sort sorter) {

        Pharmacy pharmacy = pharmacyRepository.getPharmacyByIdAndFetchWorkplaces(id);
        if (pharmacy == null) throw new RuntimeException("There's no pharmacies in database!");

        Date requestedDateAndTime = new Date(date);
        Date requestedDateAndTimeEnd = new Date(date);
        Date today = new Date();
        if (requestedDateAndTime.before(today)) throw new RuntimeException("Requested date is in the past!");

        Calendar c = Calendar.getInstance();
        c.setTime(requestedDateAndTime);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        List<PharmacyWorker> chosenWorkers = new ArrayList<>();
        for (Workplace wp : pharmacy.getWorkplaces()) {
            if (!wp.getWorker().getRole().getName().equals("PHARMACIST")) continue;

            for (WorkDay wd : wp.getWorkDays()) {
                if (wd.getWeekday().ordinal() + 1 == dayOfWeek) {
                    int requestedTime = c.get(Calendar.HOUR_OF_DAY);

                    if (requestedTime < wd.getStartTime() || requestedTime > wd.getEndTime()) continue;

                    boolean isPharmacistFree = true;
                    // Kad prebacimo u lazi, prepravi da kad gore dobavim apoteke, fetchujem i workere i njihove appointemnte
                    for (Appointment a : pharmacyWorkerRepository.getPharmacyWorkerForCalendar(wp.getWorker().getId()).getAppointmentList()) {
                        if(id.equals(a.getPharmacy().getId()) && a.getAppointmentState() == AppointmentState.CANCELLED) continue;
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

    @Override
    public List<PharmacyWorker> getDermatologistsByPatientId(Long id) {

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) return null;

        List<PharmacyWorker> dermatologists = pharmacyWorkerRepository.getDermatologistsFetchFinishedCheckups();
        return getChosenWorkers(dermatologists, id);
    }

    @Override
    public List<PharmacyWorker> getPharmacistsByPatientId(Long id) {

        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isEmpty()) return null;

        List<PharmacyWorker> pharmacists = pharmacyWorkerRepository.getPharmacistsFetchFinishedConsultations();
        return getChosenWorkers(pharmacists, id);
    }

    private List<PharmacyWorker> getChosenWorkers(List<PharmacyWorker> workers, long id) {

        List<PharmacyWorker> chosenWorkers = new ArrayList<>();

        for (PharmacyWorker pw : workers) {
            for (Appointment a : pw.getAppointmentList()) {
                if (a.getPatient().getId().equals(id)) {
                    chosenWorkers.add(pw);
                    break;
                }
            }
        }
        return chosenWorkers;
    }
}
