package com.team11.PharmacyProject.dto.offer;

public class OfferAcceptDTO {

    Long selectedOfferId;

    Long orderId;

    public OfferAcceptDTO(Long selectedOfferId, Long orderId) {
        this.selectedOfferId = selectedOfferId;
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getSelectedOfferId() {
        return selectedOfferId;
    }

    public void setSelectedOfferId(Long selectedOfferId) {
        this.selectedOfferId = selectedOfferId;
    }
}
