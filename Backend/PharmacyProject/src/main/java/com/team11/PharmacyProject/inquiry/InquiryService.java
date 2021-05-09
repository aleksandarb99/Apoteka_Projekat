package com.team11.PharmacyProject.inquiry;

import java.util.List;

public interface InquiryService {

    List<Inquiry> getInquiriesByPharmacyId(Long id);

    void save(Inquiry i);
}
