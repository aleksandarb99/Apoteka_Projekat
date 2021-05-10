package com.team11.PharmacyProject.medicineFeatures.medicineForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineFormServiceImpl implements MedicineFormService {

    @Autowired
    MedicineFormRepository medicineFormRepository;

    @Override
    public List<MedicineForm> getAllForms() {
        return medicineFormRepository.findAll();
    }
}
