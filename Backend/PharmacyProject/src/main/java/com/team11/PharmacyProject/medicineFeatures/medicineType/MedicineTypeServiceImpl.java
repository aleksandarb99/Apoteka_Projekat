package com.team11.PharmacyProject.medicineFeatures.medicineType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicineTypeServiceImpl implements MedicineTypeService {

    @Autowired
    MedicineTypeRepository medicineTypeRepository;

    @Override
    public List<MedicineType> getAllTypes() {
        return medicineTypeRepository.findAll();
    }

    @Override
    public void addNew(MedicineType mt) {
        medicineTypeRepository.save(mt);
    }
}
