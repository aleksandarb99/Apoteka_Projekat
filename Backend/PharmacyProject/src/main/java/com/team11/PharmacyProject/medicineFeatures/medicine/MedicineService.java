package com.team11.PharmacyProject.medicineFeatures.medicine;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface MedicineService {

    Medicine findOne(long id);
    List<Medicine> getAllMedicines();

    List<Medicine> getNotExistingMedicineFromPharmacy(long id);

    boolean insertMedicine(Medicine medicine);

    boolean delete(long id);

    boolean update(long id, Medicine medicine);

    Medicine getMedicineById(Long id);

    ByteArrayInputStream getMedicinePdf(long medicineId);
}
