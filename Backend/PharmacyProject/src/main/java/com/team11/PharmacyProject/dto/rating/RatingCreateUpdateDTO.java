package com.team11.PharmacyProject.dto.rating;

import com.team11.PharmacyProject.enums.GradedType;
import com.team11.PharmacyProject.rating.Rating;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class RatingCreateUpdateDTO {

    private Long id;
    @Min(1)
    @Max(5)
    private int grade;
    private Long gradedID;
    private Long date;
    private Long patientId;
    private GradedType type;

    public RatingCreateUpdateDTO() {
    }

    public RatingCreateUpdateDTO(Long id, @Min(1)
    @Max(5) int grade, Long gradedID, Long date, Long patientId, GradedType type) {
        this.id = id;
        this.grade = grade;
        this.gradedID = gradedID;
        this.date = date;
        this.patientId = patientId;
        this.type = type;
    }

    public RatingCreateUpdateDTO(Rating rating) {
        setId(rating.getId());
        setGrade(rating.getGrade());
        setGradedID(rating.getGradedID());
        setDate(rating.getDate());
        setPatientId(rating.getPatient().getId());
        setType(rating.getGradedType());
    }

    public GradedType getType() {
        return type;
    }

    public void setType(GradedType type) {
        this.type = type;
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
