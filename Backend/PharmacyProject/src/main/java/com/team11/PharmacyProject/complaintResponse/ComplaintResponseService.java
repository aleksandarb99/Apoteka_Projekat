package com.team11.PharmacyProject.complaintResponse;

import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseDTO;

public interface ComplaintResponseService {
    ComplaintResponse getComplaintResponse(long complaintId);

    boolean submitResponse(ComplaintResponseDTO complaintResponseDTO);
}
