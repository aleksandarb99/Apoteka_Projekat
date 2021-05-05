package com.team11.PharmacyProject.dto.order;


import com.team11.PharmacyProject.enums.OrderState;

import java.util.List;

public class MyOrderDTO {

    private Long id;

    private Long deadline;

    private List<OrderItemDTO> orderItem;

    private OrderState orderState;

    public MyOrderDTO() {
    }

    public MyOrderDTO(Long id, Long deadline, List<OrderItemDTO> orderItem, OrderState orderState) {
        this.id = id;
        this.deadline = deadline;
        this.orderItem = orderItem;
        this.orderState = orderState;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
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
