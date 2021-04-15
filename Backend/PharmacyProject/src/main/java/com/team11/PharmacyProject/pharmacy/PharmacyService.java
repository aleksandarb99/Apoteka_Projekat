package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;

import java.util.List;

public interface PharmacyService {

    Pharmacy getPharmacyById(Long id);

    List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue);

    List<Pharmacy> filterPharmacies(String gradeValue, String distanceValue, double longitude, double latitude);

    boolean doFilteringByGrade(double avgGrade, String gradeValue);

    boolean doFilteringByDistance(Address address, String distanceValue, double longitude, double latitude);

    double calculateDistance(Address address, double lon2, double lat2);

    double getMedicineItemPrice(Long pharmacyId, Long medicineItemId);

    double calculatePrice(List<MedicinePrice> prices);

    boolean insertPharmacy(Pharmacy pharmacy);

    boolean delete(long id);

    boolean update(long id, Pharmacy pharmacy);

    List<Pharmacy> getAll();

    List<Pharmacy> getPharmaciesByMedicineId(Long id);
}
