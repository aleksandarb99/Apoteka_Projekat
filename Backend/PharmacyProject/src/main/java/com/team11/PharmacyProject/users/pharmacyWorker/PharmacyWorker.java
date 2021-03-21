package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.users.user.User;
import com.team11.PharmacyProject.workCalendar.WorkCalendar;

import java.util.List;

public abstract class PharmacyWorker extends User {
	private double avgGrade;
	private WorkCalendar workCalendar;
	private List<Appointment> appointmentList;



	public PharmacyWorker(Long id, String password, String firstName, String lastName, String email,
						  String telephone, UserType userType, Address address, double avgGrade, WorkCalendar calendar,List<Appointment> list) {
		super(id,password, firstName, lastName, email, telephone, userType, address);
		this.avgGrade = avgGrade;
		this.workCalendar = calendar;
		this.appointmentList = list;
	}

	public double getAvgGrade() {
		return avgGrade;
	}

	public void setAvgGrade(double avgGrade) {
		this.avgGrade = avgGrade;
	}

	public WorkCalendar getWorkCalendar() {
		return workCalendar;
	}

	public void setWorkCalendar(WorkCalendar workCalendar) {
		this.workCalendar = workCalendar;
	}

	public List<Appointment> getAppointmentList() {
		return appointmentList;
	}

	public void setAppointmentList(List<Appointment> appointmentList) {
		this.appointmentList = appointmentList;
	}
}