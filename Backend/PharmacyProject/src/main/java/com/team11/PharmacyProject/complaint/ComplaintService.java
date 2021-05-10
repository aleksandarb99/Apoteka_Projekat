package com.team11.PharmacyProject.complaint;

import com.team11.PharmacyProject.dto.complaint.ComplaintCrudDTO;

import java.util.List;

public interface ComplaintService {
    boolean addComplaint(ComplaintCrudDTO complaintCrudDTO);
    List<Complaint> getComplaintsForPatient(long patientId);

    List<Complaint> getComplaints();

    List<Complaint> getNewComplaints();
}
