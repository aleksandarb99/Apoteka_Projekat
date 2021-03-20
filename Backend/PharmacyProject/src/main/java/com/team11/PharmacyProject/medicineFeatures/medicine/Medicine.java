package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.enums.RecipeRegime;
import com.team11.PharmacyProject.medicineFeatures.manufacturer.Manufacturer;
import com.team11.PharmacyProject.medicineFeatures.medicineForm.MedicineForm;
import com.team11.PharmacyProject.medicineFeatures.medicineType.MedicineType;

import java.util.*;

public class Medicine {
   private Long id;
   private String name;
   private String code;
   private String content;
   private String sideEffects;
   private double dailyIntake;
   private RecipeRegime recipeRequired;
   private String additionalNotes;
   private double avgGrade;
   private int points;
   private List<Medicine> alternativeMedicine;
   private MedicineType medicineType;
   private MedicineForm medicineForm;
   private Manufacturer manufacturer;

   public Medicine() {

   }

   public Medicine(Long id, String name, String code, String content, String sideEffects, double dailyIntake, RecipeRegime recipeRequired, String additionalNotes, double avgGrade, int points, List<Medicine> alternativeMedicine, MedicineType medicineType, MedicineForm medicineForm, Manufacturer manufacturer) {
      this.id = id;
      this.name = name;
      this.code = code;
      this.content = content;
      this.sideEffects = sideEffects;
      this.dailyIntake = dailyIntake;
      this.recipeRequired = recipeRequired;
      this.additionalNotes = additionalNotes;
      this.avgGrade = avgGrade;
      this.points = points;
      this.alternativeMedicine = alternativeMedicine;
      this.medicineType = medicineType;
      this.medicineForm = medicineForm;
      this.manufacturer = manufacturer;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
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

   public List<Medicine> getAlternativeMedicine() {
      return alternativeMedicine;
   }

   public void setAlternativeMedicine(List<Medicine> alternativeMedicine) {
      this.alternativeMedicine = alternativeMedicine;
   }

   public MedicineType getMedicineType() {
      return medicineType;
   }

   public void setMedicineType(MedicineType medicineType) {
      this.medicineType = medicineType;
   }

   public MedicineForm getMedicineForm() {
      return medicineForm;
   }

   public void setMedicineForm(MedicineForm medicineForm) {
      this.medicineForm = medicineForm;
   }

   public Manufacturer getManufacturer() {
      return manufacturer;
   }

   public void setManufacturer(Manufacturer manufacturer) {
      this.manufacturer = manufacturer;
   }
}