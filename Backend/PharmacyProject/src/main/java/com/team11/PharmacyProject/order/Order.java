package com.team11.PharmacyProject.order;

import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import java.time.LocalDate;
import java.util.List;

public class Order {
   private Long id;
   private LocalDate deadline;
   private Pharmacy pharmacy;
   private List<OrderItem> orderItem;
}