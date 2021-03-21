package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.enums.UserType;


public class User {
   private Long id;
   private String password;
   private String firstName;
   private String lastName;
   private String email;
   private String telephone;
   private UserType userType;
   private Address address;

   public User() {
   }

   public User(Long id, String password, String firstName, String lastName, String email, String telephone, UserType userType, Address address) {
      this.id = id;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.telephone = telephone;
      this.userType = userType;
      this.address = address;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getFirstName() {
      return firstName;
   }

   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }

   public String getLastName() {
      return lastName;
   }

   public void setLastName(String lastName) {
      this.lastName = lastName;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getTelephone() {
      return telephone;
   }

   public void setTelephone(String telephone) {
      this.telephone = telephone;
   }

   public UserType getUserType() {
      return userType;
   }

   public void setUserType(UserType userType) {
      this.userType = userType;
   }

   public Address getAddress() {
      return address;
   }

   public void setAddress(Address address) {
      this.address = address;
   }
}