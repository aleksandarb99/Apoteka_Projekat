package com.team11.PharmacyProject.complaintResponse;

import com.team11.PharmacyProject.complaint.Complaint;
import com.team11.PharmacyProject.users.user.MyUser;

import javax.persistence.*;

@Entity
public class ComplaintResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "response_text", nullable = false)
    private String responseText;

    @Column(name = "date", nullable = false)
    private Long date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private MyUser user;

    public ComplaintResponse() {
    }

    public ComplaintResponse(Long id, String responseText, Long date, Complaint complaint, MyUser user) {
        this.id = id;
        this.responseText = responseText;
        this.date = date;
        this.complaint = complaint;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

    public MyUser getUser() {
        return user;
    }

    public void setUser(MyUser user) {
        this.user = user;
    }
}