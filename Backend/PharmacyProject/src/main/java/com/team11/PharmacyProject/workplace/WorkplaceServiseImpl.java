package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.enums.UserType;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkplaceServiseImpl implements WorkplaceService {

    @Autowired
    private WorkplaceRepository workplaceRepository;

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
