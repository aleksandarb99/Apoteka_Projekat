package com.team11.PharmacyProject.dto.offer;

public class OfferAcceptDTO {

    Long selectedOfferId;

    Long orderId;

    Long adminId;

    public OfferAcceptDTO(Long selectedOfferId, Long orderId, Long adminId) {
        this.selectedOfferId = selectedOfferId;
        this.orderId = orderId;
        this.adminId = adminId;
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
