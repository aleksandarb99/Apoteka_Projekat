package com.team11.PharmacyProject.medicineFeatures.medicineItem;

public interface MedicineItemService {

    MedicineItem findById(long id);

    MedicineItem findByIdWithMedicine(long id);

    boolean insert(MedicineItem mi);
}
