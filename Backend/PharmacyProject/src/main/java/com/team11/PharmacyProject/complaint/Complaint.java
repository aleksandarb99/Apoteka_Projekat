package com.team11.PharmacyProject.complaint;

import com.team11.PharmacyProject.enums.ComplaintType;
import com.team11.PharmacyProject.users.patient.Patient;

import javax.persistence.*;

@Entity
public class Complaint {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "content", nullable = false)
   private String content;

   @Column(name = "complaint_on", nullable = false)
   private String complaintOn;

   @Column(name = "complaint_on_id", nullable = false)
   private Long complaintOnId;

   @Column(name = "type", nullable = false)
   @Enumerated(EnumType.STRING)
   private ComplaintType type;

   @Column(name = "date", nullable = false)
   private Long date;

   @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "patient_id")
   private Patient patient;

   public Complaint() {}

   public Complaint(Long id, String content, String complaintOn, Long complaintOnId, ComplaintType type, Long date, Patient patient) {
      this.id = id;
      this.content = content;
      this.complaintOn = complaintOn;
      this.complaintOnId = complaintOnId;
      this.type = type;
      this.date = date;
      this.patient = patient;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getContent() {
      return content;
   }

   public void setContent(String content) {
      this.content = content;
   }

   public String getComplaintOn() {
      return complaintOn;
   }

   public void setComplaintOn(String complaintOn) {
      this.complaintOn = complaintOn;
   }

   public Long getComplaintOnId() {
      return complaintOnId;
   }

   public void setComplaintOnId(Long complaintOnId) {
      this.complaintOnId = complaintOnId;
   }

   public ComplaintType getType() {
      return type;
   }

   public void setType(ComplaintType type) {
      this.type = type;
   }

   public Long getDate() {
      return date;
   }

   public void setDate(Long date) {
      this.date = date;
   }

   public Patient getPatient() {
      return patient;
   }

   public void setPatient(Patient patient) {
      this.patient = patient;
   }
}