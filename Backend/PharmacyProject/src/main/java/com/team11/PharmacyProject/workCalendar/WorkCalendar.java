package com.team11.PharmacyProject.workCalendar;

import com.team11.PharmacyProject.appointment.Appointment;

import javax.persistence.*;
import java.util.List;

@Entity
public class WorkCalendar {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
   private List<Appointment> appointments;

   public WorkCalendar() {}

   public WorkCalendar(Long id, List<Appointment> appointments) {
      this.id = id;
      this.appointments = appointments;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public List<Appointment> getAppointments() {
      return appointments;
   }

   public void setAppointments(List<Appointment> appointments) {
      this.appointments = appointments;
   }
}