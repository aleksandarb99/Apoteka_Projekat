package com.team11.PharmacyProject.inquiry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InquiryServiceImpl implements InquiryService {

    @Autowired
    InquiryRepository inquiryRepository;

    @Override
    public List<Inquiry> getInquiriesByPharmacyId(Long id) {
        return inquiryRepository.findAllByPharmacyId(id);
    }

    @Override
    public void save(Inquiry i) {
        inquiryRepository.save(i);
    }
}
