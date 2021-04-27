package com.team11.PharmacyProject.rating;

import com.team11.PharmacyProject.enums.GradedType;
import com.team11.PharmacyProject.users.patient.Patient;

import javax.persistence.*;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grade", nullable = false)
    private int grade;

    @Column(name = "graded_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private GradedType gradedType;

    @Column(name = "gradedID", nullable = false)
    private String gradedID;

    @Column(name = "date", nullable = false)
    private Long date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    public Rating() {
    }

    public Rating(Long id, int grade, GradedType gradedType, String gradedID, Long date, Patient patient) {
        this.id = id;
        this.grade = grade;
        this.gradedType = gradedType;
        this.gradedID = gradedID;
        this.date = date;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public GradedType getGradedType() {
        return gradedType;
    }

    public void setGradedType(GradedType gradedType) {
        this.gradedType = gradedType;
    }

    public String getGradedID() {
        return gradedID;
    }

    public void setGradedID(String gradedID) {
        this.gradedID = gradedID;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}