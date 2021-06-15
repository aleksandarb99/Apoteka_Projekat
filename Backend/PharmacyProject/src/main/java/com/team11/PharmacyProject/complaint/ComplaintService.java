package com.team11.PharmacyProject.complaint;

import com.team11.PharmacyProject.dto.complaint.ComplaintCrudDTO;
import com.team11.PharmacyProject.exceptions.CustomException;

import java.util.List;

public interface ComplaintService {
    void addComplaint(ComplaintCrudDTO complaintCrudDTO) throws CustomException;
    List<Complaint> getComplaintsForPatient(long patientId);

    List<Complaint> getComplaints();

    List<Complaint> getNewComplaints();
}
