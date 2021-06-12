package com.team11.PharmacyProject.dto.erecipe;

import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ERecipeState;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ERecipeDispenseDTO {
    private Long id;
    @NotNull
    private Long prescriptionDate;
    @NotNull
    private ERecipeState state;
    @NotBlank
    private String code;
    @NotNull
    private List<ERecipeItem> eRecipeItems;
    @NotNull
    private Long patientId;
    @NotNull
    private Long pharmacyId;
    @NotNull
    private Double totalPrice;

    public ERecipeDispenseDTO() {
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

}
