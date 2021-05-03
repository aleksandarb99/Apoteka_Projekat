package com.team11.PharmacyProject.workplace;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public boolean removeWorker(Long pharmacyId, Long workplaceId) {
        Pharmacy pharmacy = pharmacyService.getPharmacyByIdWithWorkplaces(pharmacyId);
        Workplace workplace = workplaceRepository.findByIdWithWorker(workplaceId);
        if(pharmacy == null || workplace == null)
            return false;

        List<Appointment> appointments = appointmentService.getUpcomingAppointmentsForWorkerByWorkerIdAndPharmacyId(workplace.getWorker().getId(), pharmacyId);
        if(!appointments.isEmpty()){
            return false;
        }

        workplaceRepository.delete(workplace);
        return true;
    }

    @Override
    public boolean addWorker(Long pharmacyId, Long workerId, RequestForWorkerDTO dto) {
        Pharmacy pharmacy = pharmacyService.getPharmacyByIdWithWorkplaces(pharmacyId);
        PharmacyWorker worker = pharmacyWorkerService.getOne(workerId);
        if(pharmacy == null || worker == null)
            return false;

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
            return false;

//      data is validated

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

//        pharmacyService.save(pharmacy);
        pharmacyWorkerService.save(worker);
        return true;
    }

    public List<Workplace> getWorkplacesByPharmacyId(Long pharmacyId) {
        List<Workplace> workplaces = new ArrayList<>();
        workplaceRepository.getWorkplacesByPharmacyId(pharmacyId).forEach(workplaces::add);
        return workplaces;
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
}
