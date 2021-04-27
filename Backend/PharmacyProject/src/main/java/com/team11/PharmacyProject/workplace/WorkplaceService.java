package com.team11.PharmacyProject.workplace;

import java.util.List;

public interface WorkplaceService {

    List<Workplace> getWorkplacesByPharmacyId(Long pharmacyId);

    List<Workplace> getDermatologistWorkplacesByPharmacyId(Long pharmacyId);

    List<Workplace> getWorkplacesOfDermatologist(Long workerID);

    Workplace getWorkplaceOfPharmacist(Long workerID);
}
