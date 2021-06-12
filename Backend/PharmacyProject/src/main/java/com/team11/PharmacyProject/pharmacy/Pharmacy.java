package com.team11.PharmacyProject.pharmacy;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.priceList.PriceList;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.user.MyUser;
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

    @Column(name = "consultation_price")
    private Double consultationPrice;

    @Column(name = "consultation_duration")
    private int consultationDuration;

    @Column(name = "points")
    private int pointsForAppointment;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Patient> subscribers;

    @OneToOne(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PriceList priceList;

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "pharmacy", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Workplace> workplaces;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<MyUser> admins;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

    public Pharmacy() {
    }

    public Pharmacy(Long id, String name, String description, Double avgGrade,
                    List<Patient> subscribers, PriceList priceList, ArrayList<Appointment> appointments,
                    Address address, List<Workplace> workplaces, Double consultationPrice, int consultationDuration,
                    int pointsForAppointment) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.avgGrade = avgGrade;
        this.subscribers = subscribers;
        this.priceList = priceList;
        this.appointments = appointments;
        this.address = address;
        this.workplaces = workplaces;
        this.consultationPrice = consultationPrice;
        this.consultationDuration = consultationDuration;
        this.pointsForAppointment = pointsForAppointment;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<MyUser> getAdmins() {
        return admins;
    }

    public void setAdmins(List<MyUser> admins) {
        this.admins = admins;
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

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
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

    public Double getConsultationPrice() {
        return consultationPrice;
    }

    public void setConsultationPrice(Double consultationPrice) {
        this.consultationPrice = consultationPrice;
    }

    public int getConsultationDuration() {
        return consultationDuration;
    }

    public void setConsultationDuration(int consultationDuration) {
        this.consultationDuration = consultationDuration;
    }

    public int getPointsForAppointment() {
        return pointsForAppointment;
    }

    public void setPointsForAppointment(int pointsForAppointment) {
        this.pointsForAppointment = pointsForAppointment;
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