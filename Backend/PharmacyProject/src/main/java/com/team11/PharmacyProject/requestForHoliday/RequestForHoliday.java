package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.enums.AbsenceRequestState;
import com.team11.PharmacyProject.enums.AbsenceType;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

import java.time.LocalDate;

public class RequestForHoliday {
   private Long id;
   private LocalDate startDate;
   private LocalDate endDate;
   private AbsenceRequestState requestState;
   private AbsenceType absenceType;
   private String declineText;
   private PharmacyWorker pharmacyWorker;
}