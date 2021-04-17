package com.team11.PharmacyProject.priceList;


public interface PriceListService {

    PriceList findById(long id);

    PriceList insertMedicine(long id, long medicineId);

    PriceList removeMedicine(long id, long medicineId);

    void deleteDuplicates(PriceList priceList);

    double getMedicineItemPrice(Long medicineItemId);
}
