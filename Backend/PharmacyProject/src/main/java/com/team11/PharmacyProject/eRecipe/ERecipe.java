package com.team11.PharmacyProject.eRecipe;

import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ERecipeState;
import com.team11.PharmacyProject.users.patient.Patient;

import javax.persistence.*;
import java.util.List;

@Entity
public class ERecipe {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "dispensingDate", nullable = false)
   private Long dispensingDate;

   @Column(name = "state", nullable = false)
   private ERecipeState state;

   @Column(name = "code", unique = true, nullable = false)
   private String code;

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "erecipe_id")
   private List<ERecipeItem> eRecipeItems;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "patient_id")
   private Patient patient;

   public ERecipe() {}

   public ERecipe(Long id, Long dispensingDate, ERecipeState state, String code, List<ERecipeItem> eRecipeItems, Patient patient) {
      this.id = id;
      this.dispensingDate = dispensingDate;
      this.state = state;
      this.code = code;
      this.eRecipeItems = eRecipeItems;
      this.patient = patient;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getDispensingDate() {
      return dispensingDate;
   }

   public void setDispensingDate(Long dispensingDate) {
      this.dispensingDate = dispensingDate;
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

   public Patient getPatient() {
      return patient;
   }

   public void setPatient(Patient patient) {
      this.patient = patient;
   }
}