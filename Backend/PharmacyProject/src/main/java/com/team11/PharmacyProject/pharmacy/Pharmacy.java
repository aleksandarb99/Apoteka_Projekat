package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.location.Location;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.workplace.Workplace;
import java.util.*;

public class Pharmacy {
   private Long id;
   private String name;
   private String description;
   private Double avgGrade;
   private List<Patient> subscribers;
   private PriceList priceList;
   private ArrayList<Appointment> appointments;
   private Location location;
   private List<Workplace> workplaces;

   public Pharmacy() {
   }

   public Pharmacy(Long id, String name, String description, Double avgGrade,
                   List<Patient> subscribers, PriceList priceList, ArrayList<Appointment> appointments,
                   Location location, List<Workplace> workplaces) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.avgGrade = avgGrade;
      this.subscribers = subscribers;
      this.priceList = priceList;
      this.appointments = appointments;
      this.location = location;
      this.workplaces = workplaces;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public Double getAvgGrade() {
      return avgGrade;
   }

   public void setAvgGrade(Double avgGrade) {
      this.avgGrade = avgGrade;
   }

   public List<Patient> getSubscribers() {
      return subscribers;
   }

   public void setSubscribers(List<Patient> subscribers) {
      this.subscribers = subscribers;
   }

   public PriceList getPriceList() {
      return priceList;
   }

   public void setPriceList(PriceList priceList) {
      this.priceList = priceList;
   }

   public ArrayList<Appointment> getAppointments() {
      return appointments;
   }

   public void setAppointments(ArrayList<Appointment> appointments) {
      this.appointments = appointments;
   }

   public Location getLocation() {
      return location;
   }

   public void setLocation(Location location) {
      this.location = location;
   }

   public List<Workplace> getWorkplaces() {
      return workplaces;
   }

   public void setWorkplaces(List<Workplace> workplaces) {
      this.workplaces = workplaces;
   }

   public void addWorkplace(Workplace workplace){
      workplaces.add(workplace);
   }

   public void addSubscriber(Patient patient){
      subscribers.add(patient);
   }

   public void addAppointment(Appointment appointment){
      appointments.add(appointment);
   }

   @Override
   public String toString() {
      return "Pharmacy{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", description='" + description + '\'' +
              ", avgGrade=" + avgGrade +
              ", subscribers=" + subscribers +
              ", priceList=" + priceList +
              ", appointments=" + appointments +
              ", location=" + location +
              ", workplaces=" + workplaces +
              '}';
   }
}