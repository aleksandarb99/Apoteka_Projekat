package com.team11.PharmacyProject.pharmacy;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long>, PharmacyRepositoryCustom {
    @Query("select p from Pharmacy p where lower(p.name) like lower(concat('%', ?1, '%')) or lower(p.address.city) like lower(concat('%', ?1, '%'))")
    List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE mi.amount > 0 and m.id = (:id)")
    List<Pharmacy> findPharmaciesByMedicineId(@Param("id") Long id);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl JOIN FETCH pl.medicineItems mi JOIN FETCH mi.medicine m WHERE p.id = (:pharmacyId) and m.id = (:medicineId)")
    Pharmacy findPharmacyByPharmacyAndMedicineId(@Param("pharmacyId") Long pharmacyId, @Param("medicineId") Long medicineId);

    @Query("SELECT p FROM Pharmacy p LEFT JOIN FETCH p.priceList pl WHERE p.id = (:id)")
    Pharmacy getPharmacyByIdAndPriceList(@Param("id") Long id);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.workplaces wp WHERE p.id = (:id)")
    Pharmacy getPharmacyByIdAndFetchWorkplaces(@Param("id") Long id);

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.workplaces")
    List<Pharmacy> findPharmaciesFetchWorkplaces();

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.workplaces")
    List<Pharmacy> findPharmaciesFetchWorkplaces(Sort sorter);

    @Query("SELECT p FROM Pharmacy p left join fetch p.priceList plist join fetch plist.medicineItems mi " +
            "join fetch mi.medicine med join fetch med.manufacturer join fetch med.medicineForm join fetch med.medicineType " +
            "where p.id = ?1 and mi.medicine.id not in " +
            "(select al.id from Patient pat join pat.allergies al where pat.id = ?2)")
    Pharmacy getPharmacyMedicineWithoutAllergies(Long pharmID, Long patientID);

    @Query("SELECT p FROM Pharmacy p left join fetch p.priceList plist left join fetch plist.medicineItems mi " +
            "left join fetch mi.medicine med join fetch med.manufacturer join fetch med.medicineForm join fetch med.medicineType " +
            "where p.id = ?1 and mi.amount > 0 and med.id not in " +
            "(select al.id from Patient pat join pat.allergies al where pat.id = ?2)" + //dobili smo sve iz apoteke na koje pac nije alergican i koji su na stanju
            "and med.id in " +
            "(select alt.id from Medicine req_med join req_med.alternativeMedicine alt where req_med.id = ?3)")
    Pharmacy getPharmacyWithAlternativeForMedicineNoAllergies(Long pharmID, Long patientID, Long medicineID);

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.subscribers")
    List<Pharmacy> findPharmaciesFetchSubscribed();

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.appointments a WHERE a.appointmentState = 'FINISHED'")
    List<Pharmacy> findPharmaciesFetchFinishedCheckupsAndConsultations();

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.admins")
    List<Pharmacy> findAllWithAdmins();

    @Query("SELECT distinct p FROM Pharmacy p LEFT JOIN FETCH p.subscribers WHERE p.id = ?1")
    Optional<Pharmacy> findPharmacyByIdFetchSubscribed(long pharmacyId);

    @Transactional
    @Modifying
    @Query(value = "delete from pharmacy_subscribers where pharmacy_id=?1 and subscribers_id=?2", nativeQuery = true)
    int removeSubscription(long pharmacyId, long patientId);

    @Query("SELECT DISTINCT p FROM Pharmacy p " +
            "JOIN FETCH p.priceList pl " +
            "JOIN FETCH pl.medicineItems mi " +
            "JOIN FETCH mi.medicine m " +
            "WHERE p.id = ?1")
    Optional<Pharmacy> getPharmacyByIdFetchPriceList(long pharmacyId);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.subscribers pl WHERE p.id = (:id)")
    Optional<Pharmacy> getPharmacyWithSubribers(Long id);

    @Query("SELECT p FROM Pharmacy p JOIN FETCH p.priceList pl WHERE p.id = ?1")
    Pharmacy findPharmacyFetchPriceList(Long pharmacyId);

    @Query("SELECT DISTINCT p FROM Pharmacy p LEFT JOIN FETCH p.admins a")
    List<Pharmacy> findPharmacyFetchAdmins();

    @Transactional
    @Query(nativeQuery = true, value = "INSERT INTO pharmacy_admins VALUES (?1, ?2)")
    void addAdmin(long pharmacyId, long adminId);
}
