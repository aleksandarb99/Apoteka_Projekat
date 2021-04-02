package com.team11.PharmacyProject.myOrder;

import com.team11.PharmacyProject.orderItem.OrderItem;
import com.team11.PharmacyProject.pharmacy.Pharmacy;

import javax.persistence.*;
import java.util.List;

@Entity
public class MyOrder {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "deadline", nullable = false)
   private Long deadline;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "pharmacy_id")
   private Pharmacy pharmacy;

   @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   private List<OrderItem> orderItem;

   public MyOrder() {}

   public MyOrder(Long id, Long deadline, Pharmacy pharmacy, List<OrderItem> orderItem) {
      this.id = id;
      this.deadline = deadline;
      this.pharmacy = pharmacy;
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

   public Pharmacy getPharmacy() {
      return pharmacy;
   }

   public void setPharmacy(Pharmacy pharmacy) {
      this.pharmacy = pharmacy;
   }

   public List<OrderItem> getOrderItem() {
      return orderItem;
   }

   public void setOrderItem(List<OrderItem> orderItem) {
      this.orderItem = orderItem;
   }
}