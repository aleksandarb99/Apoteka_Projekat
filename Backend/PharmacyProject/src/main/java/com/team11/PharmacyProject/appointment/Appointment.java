package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

import javax.persistence.*;

@Entity
public class Appointment {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "startTime", nullable = false)
   private Long startTime;

   @Column(name = "endTime", nullable = false)
   private Long endTime;

   @Column(name = "duration", nullable = false)
   private int duration;

   @Column(name = "appointmentState", nullable = false)
   private AppointmentState appointmentState;

   @Column(name = "info", nullable = false)
   private String info;

   @Column(name = "price", nullable = false)
   private double price;

   @Column(name = "appointmentType", nullable = false)
   private AppointmentType appointmentType;

   @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   @JoinColumn(name = "patient_id")
   private Patient patient;

   @ManyToOne(fetch = FetchType.EAGER)
   private PharmacyWorker worker;

   @ManyToOne(fetch = FetchType.EAGER)
   private Pharmacy pharmacy;

   public Appointment(Long id, Long startTime, Long endTime, int duration, AppointmentState appointmentState,
                      String info, double price, AppointmentType appointmentType, Patient patient, PharmacyWorker worker) {
      this.id = id;
      this.pharmacy = null;
      this.startTime = startTime;
      this.endTime = endTime;
      this.duration = duration;
      this.appointmentState = appointmentState;
      this.info = info;
      this.price = price;
      this.appointmentType = appointmentType;
      this.patient = patient;
      this.worker = worker;
   }

   public Appointment(Long id, Long startTime, Long endTime, int duration, AppointmentState appointmentState,
                      String info, double price, AppointmentType appointmentType, Patient patient, PharmacyWorker worker, Pharmacy pharmacy) {
      this.id = id;
      this.pharmacy = pharmacy;
      this.startTime = startTime;
      this.endTime = endTime;
      this.duration = duration;
      this.appointmentState = appointmentState;
      this.info = info;
      this.price = price;
      this.appointmentType = appointmentType;
      this.patient = patient;
      this.worker = worker;
   }

   public Appointment() {
   }

   public Pharmacy getPharmacy() {
      return pharmacy;
   }

   public void setPharmacy(Pharmacy pharmacy) {
      this.pharmacy = pharmacy;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public Long getStartTime() {
      return startTime;
   }

   public void setStartTime(Long startTime) {
      this.startTime = startTime;
   }

   public Long getEndTime() {
      return endTime;
   }

   public void setEndTime(Long endTime) {
      this.endTime = endTime;
   }

   public int getDuration() {
      return duration;
   }

   public void setDuration(int duration) {
      this.duration = duration;
   }

   public AppointmentState getAppointmentState() {
      return appointmentState;
   }

   public void setAppointmentState(AppointmentState appointmentState) {
      this.appointmentState = appointmentState;
   }

   public String getInfo() {
      return info;
   }

   public void setInfo(String info) {
      this.info = info;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public AppointmentType getAppointmentType() {
      return appointmentType;
   }

   public void setAppointmentType(AppointmentType appointmentType) {
      this.appointmentType = appointmentType;
   }

   public Patient getPatient() {
      return patient;
   }

   public void setPatient(Patient patient) {
      this.patient = patient;
   }

   public PharmacyWorker getWorker() {
      return worker;
   }

   public void setWorker(PharmacyWorker worker) {
      this.worker = worker;
   }
}