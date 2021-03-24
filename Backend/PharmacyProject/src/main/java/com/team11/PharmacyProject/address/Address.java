package com.team11.PharmacyProject.address;

import com.team11.PharmacyProject.location.Location;

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

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "location_id")
   private Location location;

   public Address() {
   }

   public Address(Long id, String street, String city, String country) {
      this.id = id;
      this.street = street;
      this.city = city;
      this.country = country;
   }

   public Address(Long id, String street, String city, String country, Location location) {
      this.id = id;
      this.street = street;
      this.city = city;
      this.country = country;
      this.location = location;
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

   public Location getLocation() {
      return location;
   }

   public void setLocation(Location location) {
      this.location = location;
   }
}