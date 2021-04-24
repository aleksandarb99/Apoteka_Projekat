package com.team11.PharmacyProject.priceList;


public interface PriceListService {

    PriceList findById(long id);

    PriceList insertMedicine(long id, long medicineId, int startingPrice);

    PriceList changePrice(long id, long medicineId, int startingPrice);

    PriceList removeMedicine(long id, long medicineId);

    double getMedicineItemPrice(Long medicineItemId);

    PriceList findByIdAndFetchMedicineItems(Long id);
}
