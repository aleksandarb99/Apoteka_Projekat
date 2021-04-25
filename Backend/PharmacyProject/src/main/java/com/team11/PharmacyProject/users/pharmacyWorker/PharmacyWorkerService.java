package com.team11.PharmacyProject.users.pharmacyWorker;

import org.springframework.data.domain.Sort;

import java.util.List;

public interface PharmacyWorkerService {
    PharmacyWorker getWorkerForCalendar(Long id);

    List<PharmacyWorker> getFreePharmacistsByPharmacyIdAndDate(Long id, long date, Sort sorter);
}
