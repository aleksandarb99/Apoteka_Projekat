package com.team11.PharmacyProject.medicineFeatures.medicine;

import com.team11.PharmacyProject.enums.RecipeRegime;
import com.team11.PharmacyProject.medicineFeatures.manufacturer.Manufacturer;
import com.team11.PharmacyProject.medicineFeatures.medicineForm.MedicineForm;
import com.team11.PharmacyProject.medicineFeatures.medicineType.MedicineType;

import javax.persistence.*;
import java.util.List;

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "side_effects")
    private String sideEffects;

    @Column(name = "daily_intake", nullable = false)
    private double dailyIntake;

    @Column(name = "recipe_required", nullable = false)
    @Enumerated(EnumType.STRING)
    private RecipeRegime recipeRequired;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @Column(name = "avg_grade")
    private double avgGrade;

    @Column(name = "points", nullable = false)
    private int points;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Medicine> alternativeMedicine;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
   @JoinColumn(name = "medicine_type_id")
   private MedicineType medicineType;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
   @JoinColumn(name = "medicine_form_id")
   private MedicineForm medicineForm;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
   @JoinColumn(name = "manufacturer_id")
   private Manufacturer manufacturer;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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