package com.team11.PharmacyProject.complaint;

import com.team11.PharmacyProject.dto.complaint.ComplaintCrudDTO;
import com.team11.PharmacyProject.enums.ComplaintState;
import com.team11.PharmacyProject.users.patient.Patient;
import com.team11.PharmacyProject.users.patient.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;

    @Autowired
    PatientRepository patientRepository;

    @Override
    public boolean addComplaint(ComplaintCrudDTO complaintCrudDTO) {
        //TODO check with invalid staff/pharmacies
        Optional<Patient> patient = patientRepository.findById(complaintCrudDTO.getPatientId());
        if (patient.isEmpty())
            return false;
        Complaint c = new Complaint(
                complaintCrudDTO.getId(),
                complaintCrudDTO.getContent(),
                complaintCrudDTO.getComplaintOn(),
                complaintCrudDTO.getComplaintOnId(),
                complaintCrudDTO.getType(),
                ComplaintState.IN_PROGRESS,
                complaintCrudDTO.getDate(),
                patient.get()
        );
        complaintRepository.save(c);
        return true;
    }

    @Override
    public List<Complaint> getComplaintsForPatient(long patientId) {
        return complaintRepository.getComplaintUsingPatientIdFetchPatient(patientId);
    }

    @Override
    public List<Complaint> getComplaints() {
        return complaintRepository.getComplaintsFetchPatient();
    }

    @Override
    public List<Complaint> getNewComplaints() {
        return complaintRepository.getNewComplaintsBeforeFetchPatient(System.currentTimeMillis());
    }

}
