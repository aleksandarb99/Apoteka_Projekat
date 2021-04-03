package com.team11.PharmacyProject.medicineFeatures.medicine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;


    @Override
    public Medicine findOne(long id) {
        Optional<Medicine> medicine = medicineRepository.findById(id);
        return medicine.orElse(null);
    }

    @Override
    public List<Medicine> getAllMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        medicineRepository.findAll().forEach(medicines::add);
        return medicines;
    }

    @Override
    public boolean insertMedicine(Medicine medicine) {
        if (medicine != null) {
            medicineRepository.save(medicine);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        if (medicineRepository.findById(id).isPresent()) {
            medicineRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean update(long id, Medicine medicine) {
        Optional<Medicine> m = medicineRepository.findById(id);
        if (m.isPresent()) {
            Medicine m2 = m.get();
            if (medicine.getCode() != null) {
                m2.setCode(medicine.getCode());
            }
            if (medicine.getName() != null) {
                m2.setName(medicine.getName());
            }
            if (medicine.getContent() != null) {
                m2.setContent(medicine.getContent());
            }
            if (medicine.getSideEffects() != null) {
                m2.setSideEffects(medicine.getSideEffects());
            }
            if (medicine.getRecipeRequired() != null) {
                m2.setRecipeRequired(medicine.getRecipeRequired());
            }
            m2.setDailyIntake(medicine.getDailyIntake());
            if (medicine.getAdditionalNotes() != null) {
                m2.setAdditionalNotes(medicine.getAdditionalNotes());
            }
            medicineRepository.save(m2);
            return true;
        } else {
            return false;
        }
    }
}
