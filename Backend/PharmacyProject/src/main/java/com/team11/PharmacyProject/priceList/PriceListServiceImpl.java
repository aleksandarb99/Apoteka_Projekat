package com.team11.PharmacyProject.priceList;

import com.team11.PharmacyProject.advertisement.Advertisement;
import com.team11.PharmacyProject.advertisement.AdvertismentService;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;
import com.team11.PharmacyProject.medicineFeatures.medicine.MedicineService;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItemService;
import com.team11.PharmacyProject.medicineFeatures.medicinePrice.MedicinePrice;
import com.team11.PharmacyProject.medicineFeatures.medicineReservation.MedicineReservationService;
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

    @Autowired
    private MedicineReservationService medicineReservationService;

    @Autowired
    private AdvertismentService advertismentService;

    @Override
    public PriceList findById(long id) {
        Optional<PriceList> priceList = priceListRepository.findById(id);
        return priceList.orElse(null);
    }

    public double getMedicineItemPrice(Long medicineItemId, Long pharmacyId) {
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

        MedicineItem mi2 = medicineItemService.findByIdWithMedicine(medicineItemId);
        double discount = 0;

        List<Advertisement> sales = advertismentService.findAllSalesWithDate(mi2.getMedicine().getId(), pharmacyId, System.currentTimeMillis());
        if(sales != null) {
            discount = sales.get(0).getDiscountPercent();
        }
        return priceLast * (100 - discount) / 100;
    }

    @Override
    public PriceList findByIdAndFetchMedicineItems(Long id) {
        return priceListRepository.findByIdAndFetchMedicineItems(id);
    }

    @Override
    public void changePrice(long id, long medicineItemId, int newPrice) {
        PriceList priceList = findByIdAndFetchMedicineItems(id);
        if (priceList == null) throw new RuntimeException("Price list with id "+ id+" does not exist!");

        MedicineItem medicineItem = medicineItemService.findById(medicineItemId);
        if (medicineItem == null) throw new RuntimeException("Medicine item with id "+ id+" does not exist!");

        if (newPrice < 0) throw new RuntimeException("Price must be greater than 0");

        MedicinePrice medicinePrice = new MedicinePrice(newPrice, new Date().getTime(), new ArrayList<>());

        boolean isThatMedicineInPricelist = false;
        for (MedicineItem mi:priceList.getMedicineItems()) {
            if(mi.getId().equals(medicineItemId)){
                mi.getMedicinePrices().add(medicinePrice);
                isThatMedicineInPricelist = true;
            }
        }
        if(!isThatMedicineInPricelist){
            throw new RuntimeException("Medicine is not in pricelist!");
        }

        priceListRepository.save(priceList);
    }

    @Override
    public void insertMedicine(long id, long medicineId, int startintPrice) {
        PriceList priceList = findByIdAndFetchMedicineItems(id);
        if (priceList == null) throw new RuntimeException("Price list with id "+ id+" does not exist!");

        Medicine medicine = medicineService.findOne(medicineId);
        if (medicine == null) throw new RuntimeException("Medicine with id "+ id+" does not exist!");

        if (startintPrice < 0) throw new RuntimeException("Starting price must be greater than 0");

        MedicinePrice medicinePrice = new MedicinePrice(startintPrice, new Date().getTime(), new ArrayList<>());
        List<MedicinePrice> list = new ArrayList<>();
        list.add(medicinePrice);

        for (MedicineItem mi:priceList.getMedicineItems()) {
            if(mi.getMedicine().getId().equals(medicineId)){
                throw new RuntimeException("Price list already have that medicine");
            }
        }

        MedicineItem medicineItem = new MedicineItem(0, list, medicine);
        priceList.getMedicineItems().add(medicineItem);
        priceListRepository.save(priceList);
    }

    @Override
    public void removeMedicine(long id, long medicineItemId) {
        PriceList priceList = findByIdAndFetchMedicineItems(id);
        if (priceList == null) throw new RuntimeException("Price list with id "+ id+" does not exist!");

        MedicineItem mi = medicineItemService.findById(medicineItemId);
        if (mi == null) throw new RuntimeException("Medicine item with id "+ medicineItemId+" does not exist!");

        boolean reserved = medicineReservationService.isMedicineItemReserved(medicineItemId);
        if (reserved) throw new RuntimeException("Medicine item is already reserved!");

        priceList.getMedicineItems().remove(mi);
        priceListRepository.save(priceList);
    }

}
