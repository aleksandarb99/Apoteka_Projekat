package com.team11.PharmacyProject.location;

import javax.persistence.*;

@Entity
public class Location {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "longitude", nullable = false)
   private double longitude;

   @Column(name = "latitude", nullable = false)
   private double latitude;

   public Location() {

   }

   public Location(double longitude, double latitude) {
      this.longitude = longitude;
      this.latitude = latitude;
   }

   public Long getId() {
      return id;
   }

   public double getLongitude() {
      return longitude;
   }

   public void setLongitude(double longitude) {
      this.longitude = longitude;
   }

   public double getLatitude() {
      return latitude;
   }

   public void setLatitude(double latitude) {
      this.latitude = latitude;
   }
}