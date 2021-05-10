package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.myOrder.MyOrder;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PharmacyService {

    boolean insertMedicine(Long pharmacyId, Long medicineId);

    Pharmacy getPharmacyById(Long id);

    Pharmacy getPharmacyByIdWithWorkplaces(Long id);

    void save(Pharmacy p);

    List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue);

    List<Pharmacy> filterPharmacies(String gradeValue, String distanceValue, double longitude, double latitude);

    boolean doFilteringByGrade(double avgGrade, String gradeValue);

    boolean doFilteringByDistance(Address address, String distanceValue, double longitude, double latitude);

    double calculateDistance(Address address, double lon2, double lat2);

    boolean insertPharmacy(Pharmacy pharmacy);

    boolean delete(long id);

    boolean update(long id, Pharmacy pharmacy);

    List<Pharmacy> getAll();

    List<Pharmacy> getPharmaciesByMedicineId(Long id);

    Pharmacy getPharmacyByIdAndPriceList(Long id);

    List<Pharmacy> getPharmaciesByFreePharmacists(long date, Sort sorter);

    Pharmacy getPharmacyWithMedicineNoAllergies(Long pharmid, Long patientid);

    Pharmacy getPharmacyWithAlternativeForMedicineNoAllergies(Long pharmid, Long patientID, Long medicineID);

    void addMedicineToStock(MyOrder order1);

    List<Pharmacy> getSubscribedPharmaciesByPatientId(Long id);

    List<Pharmacy> getPharmaciesByPatientId(Long id);

    Pharmacy getPharmacyIdByAdminId(Long id);

    boolean subscribe(long pharmacyId, long patientId);

    boolean isSubscribed(long pharmacyId, long patientId);

    boolean createInquiry(Long workerID, Long medicineItemID, Pharmacy pharmacy);
}
