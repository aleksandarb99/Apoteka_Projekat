package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.address.Address;

import java.util.List;

public interface PharmacyService {

    boolean insertMedicine(Long pharmacyId, Long medicineId);

    Pharmacy getPharmacyById(Long id);

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
}
