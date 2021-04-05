package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.enums.UserType;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.workCalendar.WorkCalendar;
import com.team11.PharmacyProject.workplace.Workplace;

import javax.persistence.*;
import java.util.List;

@Entity
public class PharmacyWorker extends MyUser {

    @Column(name = "avg_grade")
    private double avgGrade;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "workcalendar_id")
    private WorkCalendar workCalendar;

    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Workplace> workplaces;

    public PharmacyWorker() {
    }

    public PharmacyWorker(Long id, String password, String firstName, String lastName, String email, String telephone, UserType userType, Address address, double avgGrade, WorkCalendar workCalendar, List<Appointment> appointmentList, List<Workplace> workplaces, boolean isPasswordChanged) {
        super(id, password, firstName, lastName, email, telephone, userType, address, isPasswordChanged);
        this.avgGrade = avgGrade;
        this.workCalendar = workCalendar;
        this.appointmentList = appointmentList;
        this.workplaces = workplaces;
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

    public List<Workplace> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(List<Workplace> workplaces) {
        this.workplaces = workplaces;
    }
}