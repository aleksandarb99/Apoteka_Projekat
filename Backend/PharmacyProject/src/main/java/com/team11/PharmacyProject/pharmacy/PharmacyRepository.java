package com.team11.PharmacyProject.pharmacy;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    @Query("select p from Pharmacy p where lower(p.name) like lower(concat('%', ?1, '%')) or lower(p.address.city) like lower(concat('%', ?1, '%'))")
    List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE mi.amount > 0 and m.id = (:id)")
    List<Pharmacy> findPharmaciesByMedicineId(@Param("id") Long id);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE p.id = (:pharmacyId) and m.id = (:medicineId)")
    Pharmacy findPharmacyByPharmacyAndMedicineId(@Param("pharmacyId") Long pharmacyId, @Param("medicineId") Long medicineId);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl WHERE p.id = (:id)")
    Pharmacy getPharmacyByIdAndPriceList(@Param("id") Long id);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.workplaces wp WHERE p.id = (:id)")
    Pharmacy getPharmacyByIdAndFetchWorkplaces(@Param("id") Long id);

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.workplaces")
    List<Pharmacy> findPharmaciesFetchWorkplaces();

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.workplaces")
    List<Pharmacy> findPharmaciesFetchWorkplaces(Sort sorter);

    @Query("SELECT p FROM Pharmacy p LEFT JOIN FETCH p.appointments WHERE p.id = (:id)")
    Pharmacy findPharmacyByIdFetchAppointments(@Param("id") Long id);

    @Query("SELECT p FROM Pharmacy p left join fetch p.priceList plist join fetch plist.medicineItems mi " +
            "join fetch mi.medicine med join fetch med.manufacturer join fetch med.medicineForm join fetch med.medicineType " +
            "where p.id = ?1 and mi.medicine.id not in " +
            "(select al.id from Patient pat join pat.allergies al where pat.id = ?2)")
    Pharmacy getPharmacyMedicineWithoutAllergies(Long pharmID, Long patientID);

    @Query("SELECT p FROM Pharmacy p left join fetch p.priceList plist left join fetch plist.medicineItems mi " +
            "left join fetch mi.medicine med join fetch med.manufacturer join fetch med.medicineForm join fetch med.medicineType " +
            "where p.id = ?1 and mi.amount > 0 and mi.medicine.id not in " +
            "(select al.id from Patient pat left join pat.allergies al where pat.id = ?2)" + //dobili smo sve iz apoteke na koje pac nije alergican i koji su na stanju
            "and mi.medicine.id in " +
            "(select alt.id from Medicine req_med left join req_med.alternativeMedicine alt where req_med.id = ?3)")
    Pharmacy getPharmacyWithAlternativeForMedicineNoAllergies(Long pharmID, Long patientID, Long medicineID);

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.subscribers")
    List<Pharmacy> findPharmaciesFetchSubscribed();

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.appointments a WHERE a.appointmentState = 'RECEIVED'")
    List<Pharmacy> findPharmaciesFetchReceivedCheckupsAndConsultations();
}
