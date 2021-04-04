package com.team11.PharmacyProject.medicineFeatures.medicine;

import java.util.List;

public interface MedicineService {

    Medicine findOne(long id);
    List<Medicine> getAllMedicines();
    boolean insertMedicine(Medicine medicine);
    boolean delete(long id);
    boolean update(long id, Medicine medicine);
}
