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
   private List<Patient> patients;
   private PriceList priceList;
   private ArrayList<Appointment> appointments;
   private Location location;
   private List<Workplace> workplaces;
}