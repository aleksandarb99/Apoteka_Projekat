package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationWorkerDTO;

import java.util.List;
import java.util.Map;

public interface MedicineReservationService {

    MedicineReservationNotifyPatientDTO insertMedicineReservation(MedicineReservationInsertDTO dto);

    boolean isMedicineItemReserved(Long id);

    List<MedicineReservation> getReservedMedicinesByPatientId(Long id);

    MedicineReservation getMedicineReservationFromPharmacy(Long workerID, String resID);

    MedicineReservationWorkerDTO issueMedicine(Long workerID, String resID);

    void cancelReservation(Long id);

    Map<String, Integer> getInfoForReport(String period, Long pharmacyId);

    int calculateProfit(long start, long end, long pharmacyId);
}
