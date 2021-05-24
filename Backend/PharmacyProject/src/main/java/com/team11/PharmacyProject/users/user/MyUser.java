package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.address.Address;

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

   @ManyToOne(fetch = FetchType.EAGER)
   private Role role;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) //Prebacio sam address u lazy ispraviti, ako treba negde address pacijentu
   @JoinColumn(name = "address_id")
   private Address address;

   @Column(name = "is_password_changed", nullable = false)
   private boolean isPasswordChanged;

   @Column(name = "is_email_verified", nullable = false)
   private boolean emailVerified = false;

   public MyUser() {
   }

   public MyUser(Long id, String password, String firstName, String lastName, String email, String telephone, Role role, Address address, boolean isPasswordChanged) {
      this.id = id;
      this.password = password;
      this.firstName = firstName;
      this.lastName = lastName;
      this.email = email;
      this.role = role;
      this.telephone = telephone;
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

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
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

   public boolean isEmailVerified() {
      return emailVerified;
   }

   public void setEmailVerified(boolean emailVerified) {
      this.emailVerified = emailVerified;
   }
}