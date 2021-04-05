package com.team11.PharmacyProject.dto.patient;

import com.team11.PharmacyProject.address.Address;
import com.team11.PharmacyProject.appointment.Appointment;
import com.team11.PharmacyProject.users.patient.Patient;

import javax.validation.constraints.NotNull;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class PatientWorkerSearchDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String telephone;
    private Address address;
    private int points;
    private int penalties;
    private Long appointmentStart;
    private String pharmacy;

    public PatientWorkerSearchDTO(){

    }

    public PatientWorkerSearchDTO(@NotNull Patient patient, @NotNull Appointment appointment) {
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getEmail();
        this.telephone = patient.getTelephone();
        this.address = patient.getAddress();
        this.points = patient.getPoints();
        this.penalties = patient.getPenalties();
        this.appointmentStart = appointment.getStartTime();
        this.pharmacy = appointment.getPharmacy().getName();
    }

    public PatientWorkerSearchDTO(@NotNull Patient patient) { //konstruktor za dobavljanje pacijenta, ciji appt nisu sortirani
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getEmail();
        this.telephone = patient.getTelephone();
        this.address = patient.getAddress();
        this.points = patient.getPoints();
        this.penalties = patient.getPenalties();
        if (patient.getAppointments().isEmpty()){
            this.appointmentStart = 0L;
            this.pharmacy = "";
        }else{
            Optional<Appointment> latest = patient.getAppointments().stream().max(Comparator.comparing(Appointment::getStartTime));
            if (latest.isPresent()){
                this.appointmentStart = latest.get().getStartTime();
                this.pharmacy = latest.get().getPharmacy() == null ? "" : latest.get().getPharmacy().getName();
            }else{
                this.appointmentStart = 0L;
                this.pharmacy = "";
            }
        }
    }

    public PatientWorkerSearchDTO(@NotNull Patient patient, boolean ascending) { //konstruktor za dobavljanje pacijenta
        // ciji appt jesu sortirani
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.lastName = patient.getLastName();
        this.email = patient.getEmail();
        this.telephone = patient.getTelephone();
        this.address = patient.getAddress();
        this.points = patient.getPoints();
        this.penalties = patient.getPenalties();
        if (patient.getAppointments().isEmpty()){
            this.appointmentStart = 0L;
            this.pharmacy = "";
        }else {
            Appointment appt;
            if (ascending) {
                appt = patient.getAppointments().get(patient.getAppointments().size()-1); //uzimamo posl
            }else{
                appt = patient.getAppointments().get(0);
            }
            this.appointmentStart = appt.getStartTime();
            this.pharmacy = appt.getPharmacy() == null ? "" : appt.getPharmacy().getName();
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPenalties() {
        return penalties;
    }

    public void setPenalties(int penalties) {
        this.penalties = penalties;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAppointmentStart() {
        return appointmentStart;
    }

    public void setAppointmentStart(Long appointmentStart) {
        this.appointmentStart = appointmentStart;
    }

    public String getPharmacy() {
        return pharmacy;
    }

    public void setPharmacy(String pharmacy) {
        this.pharmacy = pharmacy;
    }
}
