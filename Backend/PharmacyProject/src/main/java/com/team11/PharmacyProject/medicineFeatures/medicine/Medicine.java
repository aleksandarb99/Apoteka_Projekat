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
}