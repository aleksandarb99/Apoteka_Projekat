package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.enums.UserType;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class MyUser {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "password", nullable = false)
   private String password;

   @Column(name = "first_name", nullable = false)
   private String firstName;

   @Column(name = "last_name", nullable = false)
   private String lastName;

   @Column(name = "email", unique = true, nullable = false)
   private String email;

   @Column(name = "telephone", unique = true, nullable = false)
   private String telephone;

   @Column(name = "user_type", nullable = false)
   @Enumerated(EnumType.STRING)
   private UserType userType;

   @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
   @JoinColumn(name = "address_id")
   private Address address;

   @Column(name = "is_password_changed", nullable = false)
   private boolean isPasswordChanged;

   public MyUser() {
   }

   public MyUser(Long id, String password, String firstName, String lastName, String email, String telephone, UserType userType, Address address, boolean isPasswordChanged) {
      this.id = id;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.telephone = telephone;
      this.userType = userType;
      this.address = address;
      this.isPasswordChanged = isPasswordChanged;
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

   public boolean isPasswordChanged() {
      return isPasswordChanged;
   }

   public void setPasswordChanged(boolean passwordChanged) {
      isPasswordChanged = passwordChanged;
   }
}