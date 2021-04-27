package com.team11.PharmacyProject.dto.medicine;

import com.team11.PharmacyProject.enums.RecipeRegime;
import com.team11.PharmacyProject.medicineFeatures.medicine.Medicine;

public class MedicineInfoDTO {

    private String name;
    private String code;
    private String content;
    private String sideEffects;
    private double dailyIntake;
    private RecipeRegime recipeRequired;
    private String additionalNotes;
    private double avgGrade;
    private int points;
    private String medicineType;
    private String medicineForm;
    private String manufacturer;

    public MedicineInfoDTO() {

    }

    public MedicineInfoDTO(Medicine medicine) {
        this.name = medicine.getName();
        this.code = medicine.getCode();
        this.content = medicine.getContent();
        this.sideEffects = medicine.getSideEffects();
        this.dailyIntake = medicine.getDailyIntake();
        this.recipeRequired = medicine.getRecipeRequired();
        this.additionalNotes = medicine.getAdditionalNotes();
        this.avgGrade = medicine.getAvgGrade();
        this.points = medicine.getPoints();
        this.medicineForm = medicine.getMedicineForm().getName();
        this.medicineType = medicine.getMedicineType().getName();
        this.manufacturer = medicine.getManufacturer().getName();
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

    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
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
}
