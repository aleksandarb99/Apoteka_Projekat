package com.team11.PharmacyProject.users.pharmacyWorker;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.users.user.Role;
import com.team11.PharmacyProject.workplace.Workplace;

import javax.persistence.*;
import java.util.List;

@Entity
public class PharmacyWorker extends MyUser {

    @Column(name = "avg_grade")
    private double avgGrade;

    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "worker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Workplace> workplaces;


    public PharmacyWorker() {
    }

    public PharmacyWorker(Long id, String password, String firstName, String lastName, String email, String telephone, Role userType, Address address, double avgGrade, List<Appointment> appointmentList, List<Workplace> workplaces, boolean isPasswordChanged) {
        super(id, password, firstName, lastName, email, telephone, userType, address, isPasswordChanged);
        this.avgGrade = avgGrade;
        this.appointmentList = appointmentList;
        this.workplaces = workplaces;
    }


    public double getAvgGrade() {
        return avgGrade;
    }

    public void setAvgGrade(double avgGrade) {
        this.avgGrade = avgGrade;
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

    public boolean addAppointment(Appointment appointment) {
        for (Appointment a : appointmentList) {
            if (a.getId().equals(appointment.getId())) return false;
        }
        appointmentList.add(appointment);
        return true;
    }
}