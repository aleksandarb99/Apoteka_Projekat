package com.team11.PharmacyProject.users.pharmacyWorker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyWorkerServiceImpl implements  PharmacyWorkerService{

    @Autowired
    PharmacyWorkerRepository pharmacyWorkerRepository;

    @Override
    public PharmacyWorker getWorkerForCalendar(Long id) {
        return pharmacyWorkerRepository.getPharmacyWorkerForCalendar(id);
    }
}
