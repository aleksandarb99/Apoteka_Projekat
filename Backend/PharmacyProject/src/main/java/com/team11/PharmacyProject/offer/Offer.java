package com.team11.PharmacyProject.offer;

import com.team11.PharmacyProject.enums.OfferState;
import com.team11.PharmacyProject.order.Order;

import javax.persistence.*;
import java.util.*;

@Entity
public class Offer {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "price", nullable = false)
   private double price;

   @Column(name = "deliveryDate", nullable = false)
   private Long deliveryDate;

   @Column(name = "offerState", nullable = false)
   private OfferState offerState;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "order_id")
   private Order order;

   public Offer() {}

   public Offer(Long id, double price, Long deliveryDate, OfferState offerState, Order order) {
      this.id = id;
      this.price = price;
      this.deliveryDate = deliveryDate;
      this.offerState = offerState;
      this.order = order;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public Long getDeliveryDate() {
      return deliveryDate;
   }

   public void setDeliveryDate(Long deliveryDate) {
      this.deliveryDate = deliveryDate;
   }

   public OfferState getOfferState() {
      return offerState;
   }

   public void setOfferState(OfferState offerState) {
      this.offerState = offerState;
   }

   public Order getOrder() {
      return order;
   }

   public void setOrder(Order order) {
      this.order = order;
   }
}