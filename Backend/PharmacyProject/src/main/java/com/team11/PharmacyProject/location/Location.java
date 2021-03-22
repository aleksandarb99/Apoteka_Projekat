package com.team11.PharmacyProject.location;

import com.team11.PharmacyProject.address.Address;

public class Location extends Address {
   private double longitude;
   private double latitude;

   public Location(Long id, String street, String city, String country, double longitude, double latitude) {
      super(id, street, city, country);
      this.longitude = longitude;
      this.latitude = latitude;
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