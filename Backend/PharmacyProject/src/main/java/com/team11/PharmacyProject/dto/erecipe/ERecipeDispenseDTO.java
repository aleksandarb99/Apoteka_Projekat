package com.team11.PharmacyProject.dto.erecipe;

import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ERecipeState;

import java.util.List;

public class ERecipeDispenseDTO {
    private Long id;
    private Long prescriptionDate;
    private Long dispensingDate;
    private ERecipeState state;
    private String code;
    private List<ERecipeItem> eRecipeItems;
    private Long patientId;
    private Long pharmacyId;
    private double totalPrice;
    private double totalPriceWithDiscount;

    public ERecipeDispenseDTO() {
    }

    public ERecipeDispenseDTO(Long id, Long prescriptionDate, Long dispensingDate, ERecipeState state, String code, List<ERecipeItem> eRecipeItems, Long patientId) {
        this.id = id;
        this.prescriptionDate = prescriptionDate;
        this.dispensingDate = dispensingDate;
        this.state = state;
        this.code = code;
        this.eRecipeItems = eRecipeItems;
        this.patientId = patientId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Long prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public ERecipeState getState() {
        return state;
    }

    public void setState(ERecipeState state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<ERecipeItem> geteRecipeItems() {
        return eRecipeItems;
    }

    public void seteRecipeItems(List<ERecipeItem> eRecipeItems) {
        this.eRecipeItems = eRecipeItems;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getDispensingDate() {
        return dispensingDate;
    }

    public void setDispensingDate(Long dispensingDate) {
        this.dispensingDate = dispensingDate;
    }

    public Long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalPriceWithDiscount() {
        return totalPriceWithDiscount;
    }

    public void setTotalPriceWithDiscount(double totalPriceWithDiscount) {
        this.totalPriceWithDiscount = totalPriceWithDiscount;
    }
}
