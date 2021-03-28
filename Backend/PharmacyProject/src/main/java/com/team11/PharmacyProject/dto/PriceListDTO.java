package com.team11.PharmacyProject.dto;

import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;


import java.util.List;

public class PriceListDTO {

    private Long id;

    private List<MedicineItemDTO> medicineItems;

    public PriceListDTO(Long id, List<MedicineItemDTO> medicineItems) {
        this.id = id;
        this.medicineItems = medicineItems;
    }

    public PriceListDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MedicineItemDTO> getMedicineItems() {
        return medicineItems;
    }

    public void setMedicineItems(List<MedicineItemDTO> medicineItems) {
        this.medicineItems = medicineItems;
    }
}
