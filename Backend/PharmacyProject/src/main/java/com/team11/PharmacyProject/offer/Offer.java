package com.team11.PharmacyProject.offer;

import com.team11.PharmacyProject.enums.OfferState;
import com.team11.PharmacyProject.myOrder.MyOrder;

import javax.persistence.*;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "delivery_date", nullable = false)
    private Long deliveryDate;

    @Column(name = "offer_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferState offerState;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "order_id")
    private MyOrder order;

    public Offer() {
    }

    public Offer(Long id, double price, Long deliveryDate, OfferState offerState, MyOrder order) {
        this.id = id;
        this.price = price;
        this.deliveryDate = deliveryDate;
        this.offerState = offerState;
        this.order = order;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OfferState getOfferState() {
        return offerState;
    }

    public void setOfferState(OfferState offerState) {
        this.offerState = offerState;
    }

    public MyOrder getOrder() {
        return order;
    }

    public void setOrder(MyOrder order) {
        this.order = order;
    }
}