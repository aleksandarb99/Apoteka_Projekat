package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;

public interface MedicineReservationService {

    MedicineReservationNotifyPatientDTO insertMedicineReservation(MedicineReservationInsertDTO dto);
}
