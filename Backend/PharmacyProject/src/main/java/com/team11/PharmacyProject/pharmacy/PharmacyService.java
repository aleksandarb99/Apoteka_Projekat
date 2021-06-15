package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.dto.erecipe.ERecipeDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyERecipeDTO;
import com.team11.PharmacyProject.dto.user.PharmacyWorkerInfoDTO;
import com.team11.PharmacyProject.exceptions.CustomException;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;

public interface PharmacyService {

    boolean insertMedicine(Long pharmacyId, Long medicineId);

    Pharmacy getPharmacyById(Long id);

    Pharmacy getPharmacyByIdWithWorkplaces(Long id);

    void save(Pharmacy p);

    List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue);

    void insertPharmacy(Pharmacy pharmacy, List<PharmacyWorkerInfoDTO> pharmacyAdminId) throws CustomException;

    void delete(long id) throws CustomException;

    void update(long id, Pharmacy pharmacy);

    List<Pharmacy> getAll();

    List<Pharmacy> getPharmaciesByMedicineId(Long id);

    Pharmacy getPharmacyByIdAndPriceList(Long id);

    List<Pharmacy> getPharmaciesByFreePharmacists(long date, Sort sorter);

    Pharmacy getPharmacyWithMedicineNoAllergies(Long pharmid, Long patientid);

    Pharmacy getPharmacyWithAlternativeForMedicineNoAllergies(Long pharmid, Long patientID, Long medicineID);

    List<Pharmacy> getSubscribedPharmaciesByPatientId(Long id);

    List<Pharmacy> getPharmaciesByPatientId(Long id);

    Pharmacy getPharmacyIdByAdminId(Long id);

    boolean subscribe(long pharmacyId, long patientId) throws CustomException;

    boolean unsubscribe(long pharmacyId, long patientId);

    boolean isSubscribed(long pharmacyId, long patientId);

    boolean createInquiry(Long workerID, Long medicineItemID, Pharmacy pharmacy);

    List<PharmacyERecipeDTO> getAllWithMedicineInStock(ERecipeDTO eRecipeDTO, String sortBy, String order) throws CustomException;

    Map<String, Double> getInfoForReport(String period, Long pharmacyId, int duration);

    Pharmacy getPharmacyWithSubsribers(Long pharmacyId);

    void checkIfRecipeIsInPharmacy(ERecipeDTO eRecipeDTO, Long pharmacyId);
}
