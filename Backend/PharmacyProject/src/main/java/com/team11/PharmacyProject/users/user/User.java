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
}