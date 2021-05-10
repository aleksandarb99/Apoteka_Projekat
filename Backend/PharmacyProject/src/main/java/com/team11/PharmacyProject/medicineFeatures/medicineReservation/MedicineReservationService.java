package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationWorkerDTO;

import java.util.List;

public interface MedicineReservationService {

    MedicineReservationNotifyPatientDTO insertMedicineReservation(MedicineReservationInsertDTO dto);

    boolean isMedicineItemReserved(Long id);

    List<MedicineReservation> getReservedMedicinesByPatientId(Long id);

    MedicineReservation getMedicineReservationFromPharmacy(Long workerID, String resID);

    MedicineReservationWorkerDTO issueMedicine(Long workerID, String resID);

    boolean cancelReservation(Long id);
}
