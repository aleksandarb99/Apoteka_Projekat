package com.team11.PharmacyProject.dto.medicine;

import com.team11.PharmacyProject.enums.RecipeRegime;
import com.team11.PharmacyProject.medicineFeatures.medicineForm.MedicineForm;
import com.team11.PharmacyProject.medicineFeatures.medicineType.MedicineType;

import java.util.List;

public class MedicineCrudDTO {
    private Long id;
    private String code;
    private String name;
    private String content;
    private String sideEffects;
    private RecipeRegime recipeRequired;
    private double dailyIntake;
    private int points;
    private String additionalNotes;
    private MedicineForm medicineForm;
    private MedicineType medicineType;
    private List<MedicineDTO> alternativeMedicine;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public RecipeRegime getRecipeRequired() {
        return recipeRequired;
    }

    public void setRecipeRequired(RecipeRegime recipeRequired) {
        this.recipeRequired = recipeRequired;
    }

    public double getDailyIntake() {
        return dailyIntake;
    }

    public void setDailyIntake(double dailyIntake) {
        this.dailyIntake = dailyIntake;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public MedicineForm getMedicineForm() {
        return medicineForm;
    }

    public void setMedicineForm(MedicineForm medicineForm) {
        this.medicineForm = medicineForm;
    }

    public MedicineType getMedicineType() {
        return medicineType;
    }

    public void setMedicineType(MedicineType medicineType) {
        this.medicineType = medicineType;
    }

    public List<MedicineDTO> getAlternativeMedicine() {
        return alternativeMedicine;
    }

    public void setAlternativeMedicine(List<MedicineDTO> alternativeMedicine) {
        this.alternativeMedicine = alternativeMedicine;
    }
}
