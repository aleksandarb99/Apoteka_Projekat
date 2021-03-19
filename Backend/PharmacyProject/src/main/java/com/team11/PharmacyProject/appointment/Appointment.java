package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

import java.time.LocalDate;

public class Appointment {
   private Long id;
   private LocalDate startTime;
   private LocalDate endTime;
   private int duration;
   private AppointmentState appointmentState;
   private String info;
   private double price;
   private AppointmentType appointmentType;
   private Patient patient;
   private PharmacyWorker worker;
}