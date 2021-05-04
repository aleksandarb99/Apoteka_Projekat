package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationInsertDTO;
import com.team11.PharmacyProject.dto.medicineReservation.MedicineReservationNotifyPatientDTO;

import java.util.Collection;
import java.util.List;

public interface MedicineReservationService {

    MedicineReservationNotifyPatientDTO insertMedicineReservation(MedicineReservationInsertDTO dto);

    boolean isMedicineItemReserved(Long id);

    List<MedicineReservation> getReservedMedicinesByPatientId(Long id);

    boolean cancelReservation(Long id);
}
