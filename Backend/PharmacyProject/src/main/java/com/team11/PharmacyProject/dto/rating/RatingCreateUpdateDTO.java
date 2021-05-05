package com.team11.PharmacyProject.dto.rating;

import com.team11.PharmacyProject.rating.Rating;

public class RatingCreateUpdateDTO {

    private Long id;
    private int grade;
    private Long gradedID;
    private Long date;
    private Long patientId;

    public RatingCreateUpdateDTO() {}

    public RatingCreateUpdateDTO(Long id, int grade, Long gradedID, Long date, Long patientId) {
        this.id = id;
        this.grade = grade;
        this.gradedID = gradedID;
        this.date = date;
        this.patientId = patientId;
    }

    public RatingCreateUpdateDTO(Rating rating) {
        setId(rating.getId());
        setGrade(rating.getGrade());
        setGradedID(rating.getGradedID());
        setDate(rating.getDate());
        setPatientId(rating.getPatient().getId());
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

    public Long getGradedID() {
        return gradedID;
    }

    public void setGradedID(Long gradedID) {
        this.gradedID = gradedID;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
