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
    @JoinColumn(name = "complaint_id", unique = true)
    private Complaint complaint;

    // Ko je odgovorio na zalbu
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private MyUser admin;

    @Version
    @Column(name = "version", columnDefinition = "integer DEFAULT 0", nullable = false)
    private int version;

    public ComplaintResponse() {
    }

    public ComplaintResponse(Long id, String responseText, Long date, Complaint complaint, MyUser user) {
        this.id = id;
        this.responseText = responseText;
        this.date = date;
        this.complaint = complaint;
        this.admin = user;
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

    public MyUser getAdmin() {
        return admin;
    }

    public void setAdmin(MyUser admin) {
        this.admin = admin;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}