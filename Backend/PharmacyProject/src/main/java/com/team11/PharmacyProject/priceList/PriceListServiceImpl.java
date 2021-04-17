package com.team11.PharmacyProject.priceList;

import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemService;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PriceListServiceImpl implements PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineItemService medicineItemService;

    @Override
    public PriceList findById(long id) {
        Optional<PriceList> priceList = priceListRepository.findById(id);
        return priceList.orElse(null);
    }

    public double getMedicineItemPrice(Long medicineItemId) {
        MedicineItem mi = medicineItemService.findById(medicineItemId);
        if (mi == null) {
            return -1;
        }
        long max = 0;
        double priceLast = 0;
        for (MedicinePrice price : mi.getMedicinePrices()) {
            if (price.getStartDate() > max) {
                max = price.getStartDate();
                priceLast = price.getPrice();
            }
        }
        return priceLast;
    }

    @Override
    public PriceList insertMedicine(long id, long medicineId) {
        PriceList priceList = findById(id);
        if (priceList == null) return null;
        deleteDuplicates(priceList);

        Medicine medicine = medicineService.findOne(medicineId);
        if (medicine == null) return null;

        MedicinePrice medicinePrice = new MedicinePrice(0, new Date().getTime(), new ArrayList<>());
        List<MedicinePrice> list = new ArrayList<>();
        list.add(medicinePrice);

        for (MedicineItem mi:priceList.getMedicineItems()) {
            if(mi.getMedicine().getId().equals(medicineId)){
                return null;
            }
        }

        MedicineItem medicineItem = new MedicineItem(0, list, medicine);
        priceList.getMedicineItems().add(medicineItem);
        priceListRepository.save(priceList);
        priceList = findById(id);
        return priceList;
    }

    @Override
    public PriceList removeMedicine(long id, long medicineItemId) {
        PriceList priceList = findById(id);
        if (priceList == null) return null;
        deleteDuplicates(priceList);


        MedicineItem mi = medicineItemService.findById(medicineItemId);
        if (mi == null) return null;

//        TODO proveri da li je rezervisan
//        TODO razmisli sta se sve brise

        priceList.getMedicineItems().remove(mi);
        priceListRepository.save(priceList);
        return priceList;
    }

    public void deleteDuplicates(PriceList priceList) {
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < priceList.getMedicineItems().size(); i++) {
            for (int j = i + 1; j < priceList.getMedicineItems().size(); j++) {
                if (priceList.getMedicineItems().get(i).getId().equals(priceList.getMedicineItems().get(j).getId())) {
                    indexes.add(i);
                }
            }
        }
        for (int i :
                indexes) {
            priceList.getMedicineItems().remove(i);
        }

    }
}
