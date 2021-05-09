package com.team11.PharmacyProject.dto.order;

import java.util.List;

public class MyOrderAddingDTO {

    private Long pharmacyId;

    private Long deadline;

    private Long adminId;

    private List<OrderItemAddingDTO> items;

    public MyOrderAddingDTO(Long pharmacyId, Long deadline, Long adminId, List<OrderItemAddingDTO> items) {
        this.pharmacyId = pharmacyId;
        this.deadline = deadline;
        this.adminId = adminId;
        this.items = items;
    }

    public MyOrderAddingDTO() {
    }

    public void setPharmacyId(Long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public long getPharmacyId() {
        return pharmacyId;
    }

    public void setPharmacyId(long pharmacyId) {
        this.pharmacyId = pharmacyId;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public List<OrderItemAddingDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemAddingDTO> items) {
        this.items = items;
    }
}
