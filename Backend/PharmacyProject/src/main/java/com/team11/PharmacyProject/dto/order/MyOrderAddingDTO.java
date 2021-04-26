package com.team11.PharmacyProject.dto.order;

import java.util.List;

public class MyOrderAddingDTO {

    private Long pharmacyId;

    private Long deadline;

    private List<OrderItemAddingDTO> items;

    public MyOrderAddingDTO(long pharmacyId, Long deadline, List<OrderItemAddingDTO> items) {
        this.pharmacyId = pharmacyId;
        this.deadline = deadline;
        this.items = items;
    }

    public MyOrderAddingDTO() {
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
