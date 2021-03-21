package com.team11.PharmacyProject.complaintResponse;

import com.team11.PharmacyProject.complaint.Complaint;
import com.team11.PharmacyProject.users.user.User;

import java.time.LocalDate;

public class ComplaintResponse {
   private Long id;
   private String responseText;
   private LocalDate date;
   private Complaint complaint;
   private User user;
}