
package com.team11.PharmacyProject.medicineFeatures.medicineItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MedicineItemServiceImpl implements MedicineItemService {

    @Autowired
    MedicineItemRepository medicineItemRepository;

    @Override
    public MedicineItem findByIdWithMedicine(long id) {
        return medicineItemRepository.findByIdWithMedicine(id);
    }

    @Override
    public MedicineItem findById(long id) {
        return medicineItemRepository.findById(id).get();
    }

    @Override
    public boolean insert(MedicineItem mi) {
        if (mi != null) {
            medicineItemRepository.save(mi);
            return true;
        } else {
            return false;
        }
    }


}