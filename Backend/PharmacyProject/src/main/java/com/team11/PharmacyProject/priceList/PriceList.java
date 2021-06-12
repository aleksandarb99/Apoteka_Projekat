package com.team11.PharmacyProject.priceList;

import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import javax.persistence.*;
import java.util.List;

@Entity
public class PriceList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pharmacy_id")
    private Long id;

    @OneToMany(mappedBy = "priceList", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MedicineItem> medicineItems;

    @OneToOne
    @MapsId
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    public PriceList(Long id, List<MedicineItem> medicineItems) {
        this.id = id;
        this.medicineItems = medicineItems;
    }

    public PriceList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<MedicineItem> getMedicineItems() {
        return medicineItems;
    }

    public void setMedicineItems(List<MedicineItem> medicineItems) {
        this.medicineItems = medicineItems;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public void addMedicineItem(MedicineItem item) {
        medicineItems.add(item);
    }
}