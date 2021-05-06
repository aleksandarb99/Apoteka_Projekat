package com.team11.PharmacyProject.inquiry;

import com.team11.PharmacyProject.dto.inquiry.InquiryDTO;
import com.team11.PharmacyProject.dto.order.MyOrderDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<InquiryDTO>> getInquiriesByPharmacyId(@PathVariable("id") Long id) {
        List<InquiryDTO> inquiryListDTOS = inquiryService.getInquiriesByPharmacyId(id).stream().map(m -> modelMapper.map(m, InquiryDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(inquiryListDTOS, HttpStatus.OK);
    }

}
