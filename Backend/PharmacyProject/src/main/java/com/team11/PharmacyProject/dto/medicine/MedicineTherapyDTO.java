package com.team11.PharmacyProject.dto.medicine;

import com.team11.PharmacyProject.enums.RecipeRegime;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;

public class MedicineTherapyDTO {
    private Long medicineID;
    private Long medicineItemID;
    private String name;
    private String code;
    private String content;
    private String sideEffects;
    private double dailyIntake;
    private RecipeRegime recipeRequired;
    private String additionalNotes;
    private String medicineType;
    private String medicineForm;
    private String manufacturer;
    private double price;
    private int amount;

    public MedicineTherapyDTO() {

    }

    public MedicineTherapyDTO(MedicineItem medicineItem) {
        this.medicineItemID = medicineItem.getId();
        this.medicineID = medicineItem.getMedicine().getId();
        this.name = medicineItem.getMedicine().getName();
        this.code = medicineItem.getMedicine().getCode();
        this.content = medicineItem.getMedicine().getContent();
        this.sideEffects = medicineItem.getMedicine().getSideEffects();
        this.dailyIntake = medicineItem.getMedicine().getDailyIntake();
        this.recipeRequired = medicineItem.getMedicine().getRecipeRequired();
        this.additionalNotes = medicineItem.getMedicine().getAdditionalNotes();
        this.medicineType = medicineItem.getMedicine().getMedicineType().getName();
        this.medicineForm = medicineItem.getMedicine().getMedicineForm().getName();
        this.manufacturer = medicineItem.getMedicine().getManufacturer().getName();
        //todo ovo
        this.price = medicineItem.getMedicinePrices().get(medicineItem.getMedicinePrices().size() - 1).getPrice();
        this.amount = medicineItem.getAmount();

    }

    public Long getMedicineID() {
        return medicineID;
    }

    public void setMedicineID(Long medicineID) {
        this.medicineID = medicineID;
    }

    public Long getMedicineItemID() {
        return medicineItemID;
    }

    public void setMedicineItemID(Long medicineItemID) {
        this.medicineItemID = medicineItemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    public double getDailyIntake() {
        return dailyIntake;
    }

    public void setDailyIntake(double dailyIntake) {
        this.dailyIntake = dailyIntake;
    }

    public RecipeRegime getRecipeRequired() {
        return recipeRequired;
    }

    public void setRecipeRequired(RecipeRegime recipeRequired) {
        this.recipeRequired = recipeRequired;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(String medicineType) {
        this.medicineType = medicineType;
    }

    public String getMedicineForm() {
        return medicineForm;
    }

    public void setMedicineForm(String medicineForm) {
        this.medicineForm = medicineForm;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
