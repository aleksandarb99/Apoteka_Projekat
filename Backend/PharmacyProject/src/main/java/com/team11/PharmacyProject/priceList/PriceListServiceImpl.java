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
    public PriceList findByIdAndFetchMedicineItems(Long id) {
        return priceListRepository.findByIdAndFetchMedicineItems(id);
    }

    @Override
    public PriceList changePrice(long id, long medicineItemId, int newPrice) {
        PriceList priceList = findByIdAndFetchMedicineItems(id);
        if (priceList == null) return null;

        MedicineItem medicineItem = medicineItemService.findById(medicineItemId);
        if (medicineItem == null) return null;

        if (newPrice < 0) return null;

        MedicinePrice medicinePrice = new MedicinePrice(newPrice, new Date().getTime(), new ArrayList<>());

        boolean isThatMedicineInPricelist = false;
        for (MedicineItem mi:priceList.getMedicineItems()) {
            if(mi.getId().equals(medicineItemId)){
                mi.getMedicinePrices().add(medicinePrice);
                isThatMedicineInPricelist = true;
            }
        }
        if(!isThatMedicineInPricelist){
            return null;
        }

        priceListRepository.save(priceList);
        priceList = findByIdAndFetchMedicineItems(id);
        return priceList;
    }

    @Override
    public PriceList insertMedicine(long id, long medicineId, int startintPrice) {
        PriceList priceList = findByIdAndFetchMedicineItems(id);
        if (priceList == null) return null;

        Medicine medicine = medicineService.findOne(medicineId);
        if (medicine == null) return null;

        if (startintPrice < 0) return null;

        MedicinePrice medicinePrice = new MedicinePrice(startintPrice, new Date().getTime(), new ArrayList<>());
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
        priceList = findByIdAndFetchMedicineItems(id);
        return priceList;
    }

    @Override
    public PriceList removeMedicine(long id, long medicineItemId) {
        PriceList priceList = findByIdAndFetchMedicineItems(id);
        if (priceList == null) return null;

        MedicineItem mi = medicineItemService.findById(medicineItemId);
        if (mi == null) return null;

//        TODO proveri da li je rezervisan
//        TODO razmisli sta se sve brise

        priceList.getMedicineItems().remove(mi);
        priceListRepository.save(priceList);
        return priceList;
    }

}
