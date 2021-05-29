package com.team11.PharmacyProject.complaintResponse;

import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseDTO;
import com.team11.PharmacyProject.exceptions.CustomException;
import org.hibernate.dialect.lock.OptimisticEntityLockException;

import javax.persistence.OptimisticLockException;
import java.util.List;

public interface ComplaintResponseService {
    ComplaintResponse getComplaintResponse(long complaintId);

    void submitResponse(ComplaintResponseDTO complaintResponseDTO) throws CustomException;

    List<ComplaintResponse> getComplaintResponsesForAdmin(long adminId);
}
