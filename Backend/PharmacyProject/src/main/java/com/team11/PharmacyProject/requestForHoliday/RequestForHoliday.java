package com.team11.PharmacyProject.requestForHoliday;

import com.team11.PharmacyProject.enums.AbsenceRequestState;
import com.team11.PharmacyProject.enums.AbsenceType;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

import javax.persistence.*;

@Entity
public class RequestForHoliday {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "start_date", nullable = false)
   private Long startDate;

   @Column(name = "end_date", nullable = false)
   private Long endDate;

   @Column(name = "request_state", nullable = false)
   @Enumerated(EnumType.STRING)
   private AbsenceRequestState requestState;

   @Column(name = "absence_type", nullable = false)
   @Enumerated(EnumType.STRING)
   private AbsenceType absenceType;

   @Column(name = "decline_text", nullable = false)
   private String declineText;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "pharmacy_worker_id")
   private PharmacyWorker pharmacyWorker;

   public RequestForHoliday() {}

    public RequestForHoliday(Long id, Long startDate, Long endDate, AbsenceRequestState requestState, AbsenceType absenceType, String declineText, PharmacyWorker pharmacyWorker) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestState = requestState;
        this.absenceType = absenceType;
        this.declineText = declineText;
        this.pharmacyWorker = pharmacyWorker;
    }

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