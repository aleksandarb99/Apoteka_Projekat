package com.team11.PharmacyProject.dto.complaint;

import com.team11.PharmacyProject.enums.ComplaintState;
import com.team11.PharmacyProject.enums.ComplaintType;

public class ComplaintInfoDTO {
    private long id;
    private String content;
    private String complaintOn;
    private long date;
    private ComplaintType type;
    private ComplaintState state;

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

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public ComplaintType getType() {
        return type;
    }

    public void setType(ComplaintType type) {
        this.type = type;
    }

    public ComplaintState getState() {
        return state;
    }

    public void setState(ComplaintState state) {
        this.state = state;
    }
}
