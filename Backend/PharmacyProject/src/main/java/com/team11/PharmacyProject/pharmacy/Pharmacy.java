package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.workplace.Workplace;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pharmacy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "avg_grade")
    private Double avgGrade;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Patient> subscribers;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "price_list_id")
    private PriceList priceList;

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Workplace> workplaces;

    public Pharmacy() {
    }

    public Pharmacy(Long id, String name, String description, Double avgGrade,
                    List<Patient> subscribers, PriceList priceList, ArrayList<Appointment> appointments,
                    Address address, List<Workplace> workplaces) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avgGrade = avgGrade;
        this.subscribers = subscribers;
        this.priceList = priceList;
        this.appointments = appointments;
        this.address = address;
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

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Workplace> getWorkplaces() {
        return workplaces;
    }

    public void setWorkplaces(List<Workplace> workplaces) {
        this.workplaces = workplaces;
    }

    public void addWorkplace(Workplace workplace) {
        workplaces.add(workplace);
    }

    public void addSubscriber(Patient patient) {
        subscribers.add(patient);
    }

    public void addAppointment(Appointment appointment) {
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
                ", address=" + address +
                ", workplaces=" + workplaces +
                '}';
    }
}