package com.team11.PharmacyProject.complaintResponse;

import com.team11.PharmacyProject.complaint.Complaint;
import com.team11.PharmacyProject.complaint.ComplaintRepository;
import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseDTO;
import com.team11.PharmacyProject.email.EmailService;
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
    private EmailService emailService;
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
    @Transactional(
            rollbackFor = { CustomException.class }
    )
    public void submitResponse(ComplaintResponseDTO complaintResponseDTO) throws CustomException {
        MyUser admin = userRepository.findFirstById(complaintResponseDTO.getAdminId());
        if (admin == null) {
            throw new CustomException("Admin not valid");
        }
        Optional<Complaint> complaint = complaintRepository.findById(complaintResponseDTO.getComplaintId());
        if (complaint.isEmpty())
            throw new CustomException("Complaint does not exist!");

        if (complaint.get().getState() == ComplaintState.RESOLVED) {
            throw new CustomException("Already resolved");
        }

        if (complaintResponseDTO.getResponseText().length() == 0) {
            throw new CustomException("Response text?");
        }

        Complaint c = complaint.get();
        c.setState(ComplaintState.RESOLVED);

        ComplaintResponse cr = new ComplaintResponse(
                complaintResponseDTO.getResponseText(),
                complaintResponseDTO.getDate(),
                c,
                admin
        );

        complaintResponseRepository.save(cr);
        emailService.NotifyResponse(cr);
    }

    @Override
    public List<ComplaintResponse> getComplaintResponsesForAdmin(long adminId) {
        return complaintResponseRepository.findByAdminIdFetchComplaint(adminId);
    }
}
