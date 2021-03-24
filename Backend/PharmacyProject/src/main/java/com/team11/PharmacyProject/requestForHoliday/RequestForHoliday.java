package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.enums.AbsenceRequestState;
import com.team11.PharmacyProject.enums.AbsenceType;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class RequestForHoliday {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "startDate", nullable = false)
   private Long startDate;

   @Column(name = "endDate", nullable = false)
   private Long endDate;

   @Column(name = "requestState", nullable = false)
   private AbsenceRequestState requestState;

   @Column(name = "absenceType", nullable = false)
   private AbsenceType absenceType;

   @Column(name = "declineText", nullable = false)
   private String declineText;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "pharmacyWorker_id")
   private PharmacyWorker pharmacyWorker;

   public RequestForHoliday() {}

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getStartDate() {
      return startDate;
   }

   public void setStartDate(Long startDate) {
      this.startDate = startDate;
   }

   public Long getEndDate() {
      return endDate;
   }

   public void setEndDate(Long endDate) {
      this.endDate = endDate;
   }

   public AbsenceRequestState getRequestState() {
      return requestState;
   }

   public void setRequestState(AbsenceRequestState requestState) {
      this.requestState = requestState;
   }

   public AbsenceType getAbsenceType() {
      return absenceType;
   }

   public void setAbsenceType(AbsenceType absenceType) {
      this.absenceType = absenceType;
   }

   public String getDeclineText() {
      return declineText;
   }

   public void setDeclineText(String declineText) {
      this.declineText = declineText;
   }

   public PharmacyWorker getPharmacyWorker() {
      return pharmacyWorker;
   }

   public void setPharmacyWorker(PharmacyWorker pharmacyWorker) {
      this.pharmacyWorker = pharmacyWorker;
   }
}