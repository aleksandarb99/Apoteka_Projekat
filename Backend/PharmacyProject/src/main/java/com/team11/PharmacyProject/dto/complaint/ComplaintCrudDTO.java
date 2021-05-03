package com.team11.PharmacyProject.dto.complaint;

import com.team11.PharmacyProject.complaint.Complaint;
import com.team11.PharmacyProject.enums.ComplaintType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ComplaintCrudDTO {
    private long id;
    @NotBlank
    private String content;
    @NotBlank
    private String complaintOn;
    @NotNull
    private long complaintOnId;
    @NotNull
    private ComplaintType type;
    @NotNull
    private Long date;
    @NotNull
    private long patientId;

    public ComplaintCrudDTO(Complaint c) {
        this.id = c.getId();
        this.content = c.getContent();
        this.complaintOn = c.getComplaintOn();
        this.complaintOnId = c.getComplaintOnId();
        this.type = c.getType();
        this.date = c.getDate();
        this.patientId = c.getPatient().getId();
    }

    public ComplaintCrudDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getComplaintOn() {
        return complaintOn;
    }

    public void setComplaintOn(String complaintOn) {
        this.complaintOn = complaintOn;
    }

    public long getComplaintOnId() {
        return complaintOnId;
    }

    public void setComplaintOnId(long complaintOnId) {
        this.complaintOnId = complaintOnId;
    }

    public ComplaintType getType() {
        return type;
    }

    public void setType(ComplaintType type) {
        this.type = type;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public long getPatientId() {
        return patientId;
    }

    public void setPatientId(long patientId) {
        this.patientId = patientId;
    }
}
