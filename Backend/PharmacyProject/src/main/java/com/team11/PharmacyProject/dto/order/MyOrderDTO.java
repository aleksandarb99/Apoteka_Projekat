package com.team11.PharmacyProject.dto.order;


import java.util.List;

public class MyOrderDTO {

    private Long id;

    private Long deadline;

    private List<OrderItemDTO> orderItem;

    public MyOrderDTO() {
    }

    public MyOrderDTO(Long id, Long deadline, List<OrderItemDTO> orderItem) {
        this.id = id;
        this.deadline = deadline;
        this.orderItem = orderItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public List<OrderItemDTO> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemDTO> orderItem) {
        this.orderItem = orderItem;
    }
}
