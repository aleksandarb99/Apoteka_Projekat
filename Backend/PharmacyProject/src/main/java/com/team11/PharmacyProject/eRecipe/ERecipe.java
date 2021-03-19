package com.team11.PharmacyProject.eRecipe;

import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ERecipeState;
import com.team11.PharmacyProject.users.patient.Patient;

import java.time.LocalDate;
import java.util.List;

public class ERecipe {
   private Long id;
   private LocalDate dispensingDate;
   private ERecipeState state;
   private String code;
   private List<ERecipeItem> eRecipeItems;
   private Patient patient;
}