package com.team11.PharmacyProject.dto.advertisment;

import com.team11.PharmacyProject.enums.AdvertisementType;

public class AdvertismentDTORequest {

    private Long selectedRowId;

    private Long endDate;

    private String text;

    private String type;

    private int discount;

    public AdvertismentDTORequest(Long selectedRowId, Long endDate, String text, String type, int discount) {
        this.selectedRowId = selectedRowId;
        this.endDate = endDate;
        this.text = text;
        this.type = type;
        this.discount = discount;
    }

    public Long getSelectedRowId() {
        return selectedRowId;
    }

    public void setSelectedRowId(Long selectedRowId) {
        this.selectedRowId = selectedRowId;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }
}