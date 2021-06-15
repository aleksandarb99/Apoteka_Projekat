package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.exceptions.CustomException;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface MedicineService {

    Medicine findOne(long id);
    List<Medicine> getAllMedicines();

    List<Medicine> getNotExistingMedicineFromPharmacy(long id);

    void insertMedicine(Medicine medicine) throws CustomException;

    boolean delete(long id);

    boolean update(long id, Medicine medicine) throws CustomException;

    Medicine getMedicineById(Long id);

    ByteArrayInputStream getMedicinePdf(long medicineId);

    List<Medicine> getReceivedMedicinesByPatientId(Long id);

    List<Medicine> filterMedicine(String searchParams);
}
