package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PharmacyService {

    @Autowired
    PharmacyRepository pharmacyRepository;

    public Pharmacy getPharmacyById(Long id) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(id);
        return pharmacy.orElse(null);
    }

    public List<Pharmacy> searchPharmaciesByNameOrCity(String searchValue){
        return pharmacyRepository.searchPharmaciesByNameOrCity(searchValue);
    }

    public double getMedicineItemPrice(Long pharmacyId, Long medicineItemId) {
        Optional<Pharmacy> pharmacy = pharmacyRepository.findById(pharmacyId);
        if(pharmacy.isPresent()){
            Pharmacy p = pharmacy.get();
            for (MedicineItem item: p.getPriceList().getMedicineItems()) {
                if(item.getId().equals(medicineItemId)){
                    return calculatePrice(item.getMedicinePrices());
                }
            }
        }
        return 0;
    }

    public double calculatePrice(List<MedicinePrice> prices){
        long max = 0;
        double priceLast = 0;
        for (MedicinePrice price: prices) {
            if(price.getStartDate() > max){
                max = price.getStartDate();
                priceLast = price.getPrice();
            }
        }
        return priceLast;
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
        return (List<Pharmacy>)pharmacyRepository.findAll();
    }

}
