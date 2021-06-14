package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.dto.pharmacyWorker.RequestForWorkerDTO;
import com.team11.PharmacyProject.dto.worker.WorktimeDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PharmacyWorkerService {
    PharmacyWorker getWorkerForCalendar(Long id);

    List<PharmacyWorker> getFreePharmacistsByPharmacyIdAndDate(Long id, long date, Sort sorter);

    List<PharmacyWorker> getNotWorkingWorkersByPharmacyId(Long pharmacyId, RequestForWorkerDTO dto);

    PharmacyWorker getOne(Long id);

    void save(PharmacyWorker worker);

    List<PharmacyWorker> getDermatologistsByPatientId(Long id);

    List<PharmacyWorker> getPharmacistsByPatientId(Long id);

    List<PharmacyWorker> findAll();

    WorktimeDTO getWorktime(Long workerID);

    WorktimeDTO getWorktime(Long workerID, Long pharmID);

}
