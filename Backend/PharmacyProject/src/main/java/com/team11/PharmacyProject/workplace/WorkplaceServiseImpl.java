package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.appointment.AppointmentService;
import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.enums.Weekday;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.pharmacy.PharmacyService;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorkerService;
import com.team11.PharmacyProject.workDay.WorkDay;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkplaceServiseImpl implements WorkplaceService {

    @Autowired
    private WorkplaceRepository workplaceRepository;

    @Autowired
    private PharmacyService pharmacyService;

    @Autowired
    private PharmacyWorkerService pharmacyWorkerService;

    @Autowired
    private AppointmentService appointmentService;

    @Override
    public void removeWorker(Long pharmacyId, Long workplaceId) {
        Pharmacy pharmacy = pharmacyService.getPharmacyByIdWithWorkplaces(pharmacyId);
        Workplace workplace = workplaceRepository.findByIdWithWorker(workplaceId);
        if(pharmacy == null)
            throw new RuntimeException("Pharmacy with id "+pharmacyId+" does not exist!");

        if(workplace == null)
            throw new RuntimeException("Workplace with id "+workplaceId+" does not exist!");

        List<Appointment> appointments = appointmentService.getUpcomingAppointmentsForWorkerByWorkerIdAndPharmacyId(workplace.getWorker().getId(), pharmacyId);
        if(!appointments.isEmpty()){
            throw new RuntimeException("Worker has reserved appointments and he cannot be deleted!");
        }

        workplaceRepository.delete(workplace);
    }

    @Override
    public void addWorker(Long pharmacyId, Long workerId, RequestForWorkerDTO dto) {
        Pharmacy pharmacy = pharmacyService.getPharmacyByIdWithWorkplaces(pharmacyId);
        PharmacyWorker worker = pharmacyWorkerService.getOne(workerId);
        if(pharmacy == null)
            throw new RuntimeException("Pharmacy with id "+pharmacyId+" does not exist!");

        if(worker == null)
            throw new RuntimeException("Worker with id "+workerId+" does not exist!");

        boolean flag = false;
        List<PharmacyWorker> list = pharmacyWorkerService.getNotWorkingWorkersByPharmacyId(pharmacyId, dto);
        for (PharmacyWorker w:
             list) {
            if (w.getId().equals(workerId)) {
                flag = true;
                break;
            }
        }
        if(!flag)
            throw new RuntimeException("That worker already work at pharamcy!");

        List<WorkDay> workDays = new ArrayList<>();
        if(dto.isEnable1()){
            WorkDay day = new WorkDay(Weekday.MON, dto.getStartHour(), dto.getEndHour());
            workDays.add(day);
        }
        if(dto.isEnable2()){
            WorkDay day = new WorkDay(Weekday.TUE, dto.getStartHour(), dto.getEndHour());
            workDays.add(day);
        }
        if(dto.isEnable3()){
            WorkDay day = new WorkDay(Weekday.WED, dto.getStartHour(), dto.getEndHour());
            workDays.add(day);
        }
        if(dto.isEnable4()){
            WorkDay day = new WorkDay(Weekday.THU, dto.getStartHour(), dto.getEndHour());
            workDays.add(day);
        }
        if(dto.isEnable5()){
            WorkDay day = new WorkDay(Weekday.FRI, dto.getStartHour(), dto.getEndHour());
            workDays.add(day);
        }
        if(dto.isEnable6()){
            WorkDay day = new WorkDay(Weekday.SAT, dto.getStartHour(), dto.getEndHour());
            workDays.add(day);
        }
        if(dto.isEnable7()){
            WorkDay day = new WorkDay(Weekday.SUN, dto.getStartHour(), dto.getEndHour());
            workDays.add(day);
        }

        Workplace workplace = new Workplace(worker, workDays, pharmacy);
        pharmacy.addWorkplace(workplace);
        worker.getWorkplaces().add(workplace);

        pharmacyWorkerService.save(worker);
    }

    @Override
    public List<Workplace> searchWorkplacesByNameOrSurnameOfWorker(String searchValue, Long pharmacyId) {
        List<Workplace> workplaces = new ArrayList<>();
        List<Workplace> workplacesWhichAreWorkers;
        if(pharmacyId == -1)
            workplacesWhichAreWorkers = (List<Workplace>) workplaceRepository.searchWorkplacesByNameOrSurnameOfWorker(searchValue);
        else
            workplacesWhichAreWorkers = (List<Workplace>) workplaceRepository.searchWorkplacesInPharmacyByNameOrSurnameOfWorker(searchValue, pharmacyId);
        for (Workplace w: workplacesWhichAreWorkers) {
            boolean isIn = false;
            for (Workplace w2: workplaces) {
                if (w.getWorker().getId().equals(w2.getWorker().getId())) {
                    isIn = true;
                    break;
                }
            }
            if(!isIn)
                workplaces.add(w);
        }

        return workplaces;
    }

    //    Ako je pharmacyId -1, to znaci da zeli da se vracaju radnici svih apoteka
    public List<Workplace> getWorkplacesByPharmacyId(Long pharmacyId) {
        List<Workplace> workplaces = new ArrayList<>();

        if (pharmacyId == -1) {
            List<Workplace> workplacesWhichAreWorkers = (List<Workplace>) workplaceRepository.getWorkplacesWhichAreWorkers();
            for (Workplace w: workplacesWhichAreWorkers) {
                boolean isIn = false;
                for (Workplace w2: workplaces) {
                    if (w.getWorker().getId().equals(w2.getWorker().getId())) {
                        isIn = true;
                        break;
                    }
                }
                if(!isIn)
                    workplaces.add(w);
            }
        }
        else
            workplaceRepository.getWorkplacesByPharmacyId(pharmacyId).forEach(workplaces::add);

        return workplaces;
    }

    @Override
    public Map<Long, List<String>> getAllPharmacyNames() {
        Map<Long, List<String>> map = new HashMap<>();
        List<PharmacyWorker> workers = pharmacyWorkerService.findAll();
        for (PharmacyWorker w: workers) {
            List<String> names = getPharmacyNamesByWorkerId(w.getId());
            map.put(w.getId(), names);
        }
        return map;
    }

    @Override
    public List<String> getPharmacyNamesByWorkerId(Long id) {
        List<String> pharmacies = new ArrayList<>();
        List<Workplace> workplaceList = workplaceRepository.getWorkplacesOfWorker(id);

        for (Workplace w:
             workplaceList) {
            pharmacies.add(w.getPharmacy().getName());
        }
        return pharmacies;
    }

    public List<Workplace> getDermatologistWorkplacesByPharmacyId(Long pharmacyId) {
        List<Workplace> workplaces = new ArrayList<>();
        workplaceRepository.getWorkplacesByPharmacyId(pharmacyId).forEach(workplaces::add);

        for (int i = 0; i < workplaces.size(); i++) {
            if (!workplaces.get(i).getWorker().getUserType().equals(UserType.DERMATOLOGIST)) workplaces.remove(i);
        }

        return workplaces;
    }

    @Override
    public List<Workplace> getWorkplacesOfDermatologist(Long workerID) {
        return workplaceRepository.getWorkplacesOfWorker(workerID);
    }

    @Override
    public Workplace getWorkplaceOfPharmacist(Long workerID) {
        List<Workplace> workplaces = workplaceRepository.getWorkplacesOfWorker(workerID);
        if (workplaces.isEmpty()){
            return null;
        }
        return workplaces.get(0);
    }

    @Override
    public Workplace getWorkplaceOfDermatologist(Long workerID, Long pharmacyID){
        return workplaceRepository.getWorkplaceOfDermatologist(workerID, pharmacyID);
    }
}
