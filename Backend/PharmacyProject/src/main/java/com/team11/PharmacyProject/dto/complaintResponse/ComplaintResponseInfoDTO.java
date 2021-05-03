package com.team11.PharmacyProject.dto.complaintResponse;

import com.team11.PharmacyProject.dto.complaint.ComplaintInfoDTO;

public class ComplaintResponseInfoDTO {
    private String responseText;
    private ComplaintInfoDTO complaint;
    private long date;

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public ComplaintInfoDTO getComplaint() {
        return complaint;
    }

    public void setComplaint(ComplaintInfoDTO complaint) {
        this.complaint = complaint;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
