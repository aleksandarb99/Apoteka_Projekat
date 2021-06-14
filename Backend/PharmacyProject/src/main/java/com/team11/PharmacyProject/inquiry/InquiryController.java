package com.team11.PharmacyProject.inquiry;

import com.team11.PharmacyProject.dto.inquiry.InquiryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/inquiry")
public class InquiryController {

    @Autowired
    InquiryService inquiryService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "/bypharmacyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('PHARMACY_ADMIN')")
    public ResponseEntity<List<InquiryDTO>> getInquiriesByPharmacyId(@PathVariable("id") Long id) {
        List<InquiryDTO> inquiryListDTOS = inquiryService.getInquiriesByPharmacyId(id).stream().map(m -> modelMapper.map(m, InquiryDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(inquiryListDTOS, HttpStatus.OK);
    }

}
