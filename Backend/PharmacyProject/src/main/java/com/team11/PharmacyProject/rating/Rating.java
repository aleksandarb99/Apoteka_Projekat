package com.team11.PharmacyProject.rating;

import com.team11.PharmacyProject.enums.GradedType;
import com.team11.PharmacyProject.users.patient.Patient;

import java.time.LocalDate;

public class Rating {
   private Long id;
   private int grade;
   private GradedType gradedType;
   private String gradedID;
   private LocalDate date;
   private Patient patient;
}