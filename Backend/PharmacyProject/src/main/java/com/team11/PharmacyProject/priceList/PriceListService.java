package com.team11.PharmacyProject.priceList;


public interface PriceListService {

    PriceList findById(long id);

    void insertMedicine(long id, long medicineId, int startingPrice);

    void changePrice(long id, long medicineId, int startingPrice);

    void removeMedicine(long id, long medicineId);

    double getMedicineItemPrice(Long medicineItemId, Long id);

    PriceList findByIdAndFetchMedicineItems(Long id);
}
