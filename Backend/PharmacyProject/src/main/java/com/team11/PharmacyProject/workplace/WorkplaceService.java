package com.team11.PharmacyProject.workplace;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;

import java.util.List;
import java.util.Map;

public interface WorkplaceService {

    boolean addWorker(Long pharmacyId, Long workerId, RequestForWorkerDTO dto);

    boolean removeWorker(Long pharmacyId, Long workerId);

    List<Workplace> getWorkplacesByPharmacyId(Long pharmacyId);

    List<Workplace> getDermatologistWorkplacesByPharmacyId(Long pharmacyId);

    List<Workplace> getWorkplacesOfDermatologist(Long workerID);

    Workplace getWorkplaceOfPharmacist(Long workerID);

    Workplace getWorkplaceOfDermatologist(Long workerID, Long pharmacyID);

    List<String> getPharmacyNamesByWorkerId(Long id);

    List<Workplace> searchWorkplacesByNameOrSurnameOfWorker(String searchValue, Long pharmacyId);

    Map<Long, List<String>> getAllPharmacyNames();
}
