package com.team11.PharmacyProject.eRecipe;

import com.team11.PharmacyProject.eRecipeItem.ERecipeItem;
import com.team11.PharmacyProject.enums.ERecipeState;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.patient.Patient;

import javax.persistence.*;
import java.util.List;

@Entity
public class ERecipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prescription_date", nullable = false)
    private Long prescriptionDate;

    @Column(name = "dispensing_date")
    private Long dispensingDate;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ERecipeState state;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "total_price_with_discount")
    private double totalPriceWithDiscount;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ERecipeItem> eRecipeItems;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    public ERecipe() {
    }

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

    public Long getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Long prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
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

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }
}