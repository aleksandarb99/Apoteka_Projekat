package com.team11.PharmacyProject.dto.inquiry;

import com.team11.PharmacyProject.dto.medicine.MedicineItemDTO;
import com.team11.PharmacyProject.dto.pharmacy.PharmacyWorkerDTO;

public class InquiryDTO {

    private boolean active;

    private PharmacyWorkerDTO worker;

    private MedicineItemDTO medicineItems;

    private Long date;

    public InquiryDTO() {
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public PharmacyWorkerDTO getWorker() {
        return worker;
    }

    public void setWorker(PharmacyWorkerDTO worker) {
        this.worker = worker;
    }

    public MedicineItemDTO getMedicineItems() {
        return medicineItems;
    }

    public void setMedicineItems(MedicineItemDTO medicineItems) {
        this.medicineItems = medicineItems;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

}
