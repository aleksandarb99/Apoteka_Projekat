package com.team11.PharmacyProject.complaintResponse;

import com.team11.PharmacyProject.complaint.Complaint;
import com.team11.PharmacyProject.complaint.ComplaintRepository;
import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseDTO;
import com.team11.PharmacyProject.enums.ComplaintState;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.users.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ComplaintResponseServiceImpl implements ComplaintResponseService {

    @Autowired
    private ComplaintResponseRepository complaintResponseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ComplaintRepository complaintRepository;

    @Override
    public ComplaintResponse getComplaintResponse(long complaintId) {
        Optional<ComplaintResponse> ocr = complaintResponseRepository.findByIdFetchComplaint(complaintId);
        if (ocr.isEmpty())
            return null;
        else
            return ocr.get();
    }

    @Override
    @Transactional
    public void submitResponse(ComplaintResponseDTO complaintResponseDTO) throws CustomException {
        MyUser admin = userRepository.findFirstById(complaintResponseDTO.getAdminId());
        Optional<Complaint> complaint = complaintRepository.findById(complaintResponseDTO.getComplaintId());
        if (complaint.isEmpty())
            throw new CustomException("Complaint does not exist!");

        ComplaintResponse cr = new ComplaintResponse(
                complaintResponseDTO.getId(),
                complaintResponseDTO.getResponseText(),
                complaintResponseDTO.getDate(),
                complaint.get(),
                admin
        );

        Complaint c = complaint.get();
        c.setState(ComplaintState.RESOLVED);

        complaintRepository.save(c);
        complaintResponseRepository.save(cr);
    }

    @Override
    public List<ComplaintResponse> getComplaintResponsesForAdmin(long adminId) {
        return complaintResponseRepository.findByAdminIdFetchComplaint(adminId);
    }
}
