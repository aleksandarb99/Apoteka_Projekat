package com.team11.PharmacyProject.location;

import com.team11.PharmacyProject.address.Address;

public class Location extends Address {
   private double longitude;
   private double latitude;

   public Location(double longitude, double latitude) {
      this.longitude = longitude;
      this.latitude = latitude;
   }

   public Location() {
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