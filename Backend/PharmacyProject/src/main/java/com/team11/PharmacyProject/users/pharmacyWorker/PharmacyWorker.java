package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.users.user.User;
import com.team11.PharmacyProject.workCalendar.WorkCalendar;

import java.util.List;

public abstract class PharmacyWorker extends User {
	private double avgGrade;
	private WorkCalendar workCalendar;
	private List<Appointment> appointmentList;
}