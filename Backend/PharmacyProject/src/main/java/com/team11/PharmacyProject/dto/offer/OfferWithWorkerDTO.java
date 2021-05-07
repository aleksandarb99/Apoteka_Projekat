package com.team11.PharmacyProject.dto.offer;

import com.team11.PharmacyProject.enums.OfferState;
import com.team11.PharmacyProject.users.supplier.Supplier;

public class OfferWithWorkerDTO {
    private long id;
    private double price;
    private long deliveryDate;
    private OfferState offerState;
    private long orderId;
    private String worker;

    public OfferWithWorkerDTO() {
    }

    public OfferWithWorkerDTO(long id, double price, long deliveryDate, OfferState offerState, long orderId, String worker) {
        this.id = id;
        this.price = price;
        this.deliveryDate = deliveryDate;
        this.offerState = offerState;
        this.orderId = orderId;
        this.worker = worker;
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

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }
}
