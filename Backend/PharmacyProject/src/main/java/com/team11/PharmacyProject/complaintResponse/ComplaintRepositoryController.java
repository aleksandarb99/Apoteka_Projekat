package com.team11.PharmacyProject.complaintResponse;

import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseDTO;
import com.team11.PharmacyProject.dto.complaintResponse.ComplaintResponseInfoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/complaint-responses")
public class ComplaintRepositoryController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComplaintResponseService complaintResponseService;

    @GetMapping(value="/{complaintId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ComplaintResponseInfoDTO> getResponseForComplaint(@PathVariable("complaintId") long complaintId){
        ComplaintResponse cr = complaintResponseService.getComplaintResponse(complaintId);
        // cr ce biti null ako nema odgovora
        ComplaintResponseInfoDTO criDTO = modelMapper.map(cr, ComplaintResponseInfoDTO.class);
        return new ResponseEntity<>(criDTO, HttpStatus.OK);
    }

    @PostMapping(value="/{complaintId}")
    public ResponseEntity<String> submitResponse(@RequestBody @Valid ComplaintResponseDTO complaintResponseDTO) {
        if (complaintResponseService.submitResponse(complaintResponseDTO)) {
            return new ResponseEntity<>("Successfully submitted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Response not submitted", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value= "/admin/{adminId}")
    public ResponseEntity<List<ComplaintResponseInfoDTO>> getResponsesForAdmin(@PathVariable("adminId") long adminId) {
        List<ComplaintResponse> cr = complaintResponseService.getComplaintResponsesForAdmin(adminId);
        // cr ce biti prazna ako nema odgovora
        List<ComplaintResponseInfoDTO> crInfo = cr.stream().map(complaintResponse -> modelMapper.map(complaintResponse, ComplaintResponseInfoDTO.class)).collect(Collectors.toList());
        return new ResponseEntity<>(crInfo, HttpStatus.OK);
    }
}
