package com.team11.PharmacyProject.medicineFeatures.medicineType;


import java.util.List;

public interface MedicineTypeService {
    List<MedicineType> getAllTypes();

    void addNew(MedicineType mt);
}
