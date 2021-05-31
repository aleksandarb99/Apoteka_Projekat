package com.team11.PharmacyProject.appointment;

import com.team11.PharmacyProject.dto.appointment.AppointmentDTORequest;
import com.team11.PharmacyProject.enums.AppointmentState;
import com.team11.PharmacyProject.enums.AppointmentType;
import com.team11.PharmacyProject.pharmacy.Pharmacy;
import com.team11.PharmacyProject.therapyPrescription.TherapyPrescription;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.pharmacyWorker.PharmacyWorker;

import javax.persistence.*;
import java.util.List;

@Entity
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time", nullable = false)
    private Long startTime;

    @Column(name = "end_time", nullable = false)
    private Long endTime;

    @Column(name = "duration", nullable = false)
    private int duration;

    @Column(name = "appointment_state", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentState appointmentState;

    @Column(name = "info")
    private String info;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "reservation_discount", nullable = false)
    private double reservationDiscount;

    @Column(name = "appointment_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    private PharmacyWorker worker;

    @ManyToOne(fetch = FetchType.EAGER)
    private Pharmacy pharmacy;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TherapyPrescription> therapyPrescriptionList;

    @Version
    @Column(name = "optlock", columnDefinition = "integer DEFAULT 0", nullable = false)
    private Long version;

    public Appointment(Long id, Long startTime, Long endTime, int duration, AppointmentState appointmentState,
                       String info, double price, AppointmentType appointmentType, Patient patient, PharmacyWorker worker, double reservationDiscount) {
        this.id = id;
        this.pharmacy = null;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.appointmentState = appointmentState;
        this.info = info;
        this.price = price;
        this.appointmentType = appointmentType;
        this.patient = patient;
        this.worker = worker;
        this.reservationDiscount = reservationDiscount;
    }

    // TODO proveriti, mislim da ovo ne treba
    public Appointment(Long pharmacyId, Long dId, AppointmentDTORequest dto) {
        this.pharmacy = null;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
        this.appointmentState = appointmentState;
        this.info = info;
        this.price = price;
        this.appointmentType = appointmentType;
        this.patient = patient;
        this.worker = worker;
    }

    public Appointment() {
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public List<TherapyPrescription> getTherapyPrescriptionList() {
        return therapyPrescriptionList;
    }

    public void setTherapyPrescriptionList(List<TherapyPrescription> therapyPrescriptionList) {
        this.therapyPrescriptionList = therapyPrescriptionList;
    }

    public Pharmacy getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(Pharmacy pharmacy) {
        this.pharmacy = pharmacy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public AppointmentState getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(AppointmentState appointmentState) {
        this.appointmentState = appointmentState;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public AppointmentType getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(AppointmentType appointmentType) {
        this.appointmentType = appointmentType;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public PharmacyWorker getWorker() {
        return worker;
    }

    public void setWorker(PharmacyWorker worker) {
        this.worker = worker;
    }

    public double getReservationDiscount() {
        return reservationDiscount;
    }

    public void setReservationDiscount(double reservationDiscount) {
        this.reservationDiscount = reservationDiscount;
    }

    public void setPriceWithDiscout(double discount) {
        this.price = this.price * (100 - discount) / 100;
    }

    public void resetOriginalPrice() {
        this.price = this.price * 100 / (100 - reservationDiscount);
    }
}