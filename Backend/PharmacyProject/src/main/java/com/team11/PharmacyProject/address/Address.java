package com.team11.PharmacyProject.address;

import javax.persistence.*;

@Entity
public class Address {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "street", unique = true, nullable = false)
   private String street;

   @Column(name = "city", nullable = false)
   private String city;

   @Column(name = "country", nullable = false)
   private String country;

   public Address() {
   }

   public Address(Long id, String street, String city, String country) {
      this.id = id;
      this.street = street;
      this.city = city;
      this.country = country;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getStreet() {
      return street;
   }

   public void setStreet(String street) {
      this.street = street;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }
}