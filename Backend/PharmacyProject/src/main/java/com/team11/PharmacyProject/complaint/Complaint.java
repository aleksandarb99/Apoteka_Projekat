package com.team11.PharmacyProject.complaint;

import com.team11.PharmacyProject.enums.ComplaintType;
import com.team11.PharmacyProject.users.patient.Patient;

import java.time.LocalDate;

public class Complaint {
   private Long id;
   private String content;
   private String ComplaintOn;
   private String complaintOnId;
   private ComplaintType type;
   private LocalDate date;
   private Patient patient;
}