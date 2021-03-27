package com.team11.PharmacyProject.medicineFeatures.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        medicineRepository.findAll().forEach(medicines::add);
        return medicines;
    }
}
