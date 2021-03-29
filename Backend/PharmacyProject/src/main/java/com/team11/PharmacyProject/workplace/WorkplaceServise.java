package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkplaceServise {

    @Autowired
    private WorkplaceRepository workplaceRepository;

    public List<Workplace> getWorkplacesByPharmacyId(Long pharmacyId) {
        List<Workplace> workplaces = new ArrayList<>();
        workplaceRepository.getWorkplacesByPharmacyId(pharmacyId).forEach(workplaces::add);
        return workplaces;
    }
}
