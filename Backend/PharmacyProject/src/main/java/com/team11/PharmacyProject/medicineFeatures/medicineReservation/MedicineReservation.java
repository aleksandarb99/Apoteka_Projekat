package com.team11.PharmacyProject.medicineFeatures.medicineReservation;

import com.team11.PharmacyProject.enums.ReservationState;
import com.team11.PharmacyProject.medicineFeatures.medicineItem.MedicineItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import javax.persistence.*;

@Entity
public class MedicineReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pickup_date", nullable = false)
    private Long pickupDate;

    @Column(name = "reservation_date", nullable = false)
    private Long reservationDate;

    @Column(name = "reservationID", nullable = false)
    private String reservationID;

    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReservationState state;

    @Column(name = "price", nullable = false)
    private double price;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "medicine_item_id")
    private MedicineItem medicineItem;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pharmacy_id")
    private Pharmacy pharmacy;

    public MedicineReservation() {
    }

    public MedicineReservation(Long pickupDate, Long reservationDate, String reservationID, ReservationState state, MedicineItem medicineItem, Pharmacy pharmacy, double price) {
        this.pickupDate = pickupDate;
        this.reservationDate = reservationDate;
        this.reservationID = reservationID;
        this.state = state;
        this.medicineItem = medicineItem;
        this.pharmacy = pharmacy;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(Long pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public ReservationState getState() {
        return state;
    }

    public void setState(ReservationState state) {
        this.state = state;
    }

    public Long getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Long reservationDate) {
        this.reservationDate = reservationDate;
    }

    public MedicineItem getMedicineItem() {
        return medicineItem;
    }

    public void setMedicineItem(MedicineItem medicineItem) {
        this.medicineItem = medicineItem;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setPriceWithDiscout(double discountPercent) {
        this.price = this.price * (100 - discountPercent) / 100;
    }
}