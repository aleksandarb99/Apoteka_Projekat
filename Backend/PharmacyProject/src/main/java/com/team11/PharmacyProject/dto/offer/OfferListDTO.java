package com.team11.PharmacyProject.dto.offer;

import com.team11.PharmacyProject.enums.OfferState;

public class OfferListDTO {
    private long id;
    private double price;
    private long deliveryDate;
    private OfferState offerState;
    private long orderId;

    public OfferListDTO() {
    }

    public OfferListDTO(long id, double price, long deliveryDate, OfferState offerState, long orderId) {
        this.id = id;
        this.price = price;
        this.deliveryDate = deliveryDate;
        this.offerState = offerState;
        this.orderId = orderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(long deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OfferState getOfferState() {
        return offerState;
    }

    public void setOfferState(OfferState offerState) {
        this.offerState = offerState;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
