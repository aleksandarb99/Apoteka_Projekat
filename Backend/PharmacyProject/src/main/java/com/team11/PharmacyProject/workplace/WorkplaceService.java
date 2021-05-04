package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;

import java.util.List;

public interface WorkplaceService {

    boolean addWorker(Long pharmacyId, Long workerId, RequestForWorkerDTO dto);

    boolean removeWorker(Long pharmacyId, Long workerId);

    List<Workplace> getWorkplacesByPharmacyId(Long pharmacyId);

    List<Workplace> getDermatologistWorkplacesByPharmacyId(Long pharmacyId);

    List<Workplace> getWorkplacesOfDermatologist(Long workerID);

    Workplace getWorkplaceOfPharmacist(Long workerID);

    List<String> getPharmacyNamesByWorkerId(Long id);
}
