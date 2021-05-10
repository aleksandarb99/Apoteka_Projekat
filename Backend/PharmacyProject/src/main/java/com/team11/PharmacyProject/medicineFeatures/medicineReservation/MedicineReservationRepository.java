package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineReservationRepository extends JpaRepository<MedicineReservation, Long> {

    @Query("SELECT m FROM MedicineReservation m JOIN FETCH m.medicineItem WHERE m.medicineItem.id = (:id) AND m.state ='RESERVED'")
    MedicineReservation findReservationByMedicineItemId(Long id);

    @Query("SELECT m FROM MedicineReservation m JOIN FETCH m.medicineItem mi JOIN FETCH mi.medicine WHERE m.pharmacy.id = ?1 " +
            "AND m.reservationID = ?2 AND m.state ='RESERVED'")
    MedicineReservation getMedicineReservationFromPharmacy(Long pharmID, String resID);
}
