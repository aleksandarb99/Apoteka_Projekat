package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PharmacyServiceImpl implements PharmacyService {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    MedicineService medicineService;

    @Override
    public boolean insertMedicine(Long pharmacyId, Long medicineId) {
        Pharmacy p = getPharmacyById(pharmacyId);
        if (p == null) {
            return false;
        }

        Medicine medicine = medicineService.findOne(medicineId);
        if (medicine == null) {
            return false;
        }

        MedicinePrice medicinePrice = new MedicinePrice(0, new Date().getTime(), new ArrayList<>());
        List<MedicinePrice> list = new ArrayList<>();
        list.add(medicinePrice);

        MedicineItem medicineItem = new MedicineItem(0, list, medicine);

        p.getPriceList().getMedicineItems().add(medicineItem);
        pharmacyRepository.save(p);
        return true;
    }

    public Pharmacy getPharmacyById(Long id) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(id);
//        Check this
        return pharmacy.orElse(null);
    }

    public List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue) {
        return pharmacyRepository.searchPharmaciesByNameOrCity(searchValue);
    }

    public List<Pharmacy> filterPharmacies(String gradeValue, String distanceValue, double longitude, double latitude) {
        List<Pharmacy> pharmacies = pharmacyRepository.findAll();
        if (!gradeValue.equals("")) {
            pharmacies = pharmacies.stream().filter(p -> doFilteringByGrade(p.getAvgGrade(), gradeValue)).collect(Collectors.toList());
        }
        if (!distanceValue.equals("")) {
            pharmacies = pharmacies.stream().filter(p -> doFilteringByDistance(p.getAddress(), distanceValue, longitude, latitude)).collect(Collectors.toList());
        }
        return pharmacies;
    }

    public boolean doFilteringByGrade(double avgGrade, String gradeValue) {
        if (gradeValue.equals("HIGH") && avgGrade > 3.0) return true;
        if (gradeValue.equals("MEDIUM") && avgGrade == 3.0) return true;
        return gradeValue.equals("LOW") && avgGrade < 3.0;
    }

    public boolean doFilteringByDistance(Address address, String distanceValue, double longitude, double latitude) {
        if (distanceValue.equals("5LESS") && calculateDistance(address, longitude, latitude) <= 5) return true;
        if (distanceValue.equals("10LESS") && calculateDistance(address, longitude, latitude) <= 10) return true;
        return distanceValue.equals("10HIGHER") && calculateDistance(address, longitude, latitude) > 10;
    }

    public double calculateDistance(Address address, double lon2, double lat2) {
        double lat1 = address.getLocation().getLatitude();
        double lon1 = address.getLocation().getLongitude();

        if ((lat1 == lat2) && (lon1 == lon2)) {
            return 0;
        } else {
            double theta = lon1 - lon2;
            double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            return (dist * 1.609344);
        }
    }

    public boolean insertPharmacy(Pharmacy pharmacy) {
        if (pharmacy != null) {
            pharmacyRepository.save(pharmacy);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(long id) {
        if (pharmacyRepository.findById(id).isPresent()) {
            pharmacyRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public boolean update(long id, Pharmacy pharmacy) {
        Optional<Pharmacy> p = pharmacyRepository.findById(id);
        if (p.isPresent()) {
            Pharmacy p2 = p.get();
            p2.setDescription(pharmacy.getDescription());
            p2.setAddress(pharmacy.getAddress());
            p2.setName(pharmacy.getName());
            p2.setAddress(pharmacy.getAddress());
            pharmacyRepository.save(p2);
            return true;
        } else {
            return false;
        }
    }

    public List<Pharmacy> getAll() {
        return pharmacyRepository.findAll();
    }

    @Override
    public List<Pharmacy> getPharmaciesByMedicineId(Long id) {
        return pharmacyRepository.findPharmaciesByMedicineId(id);
    }

    @Override
    public Pharmacy getPharmacyByIdAndPriceList(Long id) {
        return pharmacyRepository.getPharmacyByIdAndPriceList(id);
    }

}
