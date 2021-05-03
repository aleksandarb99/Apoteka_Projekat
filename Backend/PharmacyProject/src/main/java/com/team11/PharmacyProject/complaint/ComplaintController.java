package com.team11.PharmacyProject.complaint;

import com.team11.PharmacyProject.dto.complaint.ComplaintCrudDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/complaints")
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addComplaint(@RequestBody @Valid ComplaintCrudDTO complaintCrudDTO) {
        if (complaintService.addComplaint(complaintCrudDTO)) {
            return new ResponseEntity<>("Complaint added successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error. Complaint not added", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<List<ComplaintCrudDTO>> getComplaints(@PathVariable("id") long patientId) {
        List<Complaint> complaints = complaintService.getComplaintsForPatient(patientId);
        List<ComplaintCrudDTO> complaintCrudDTOs = complaints
                .stream()
                .map(ComplaintCrudDTO::new)
                .collect(Collectors.toList());
        return new ResponseEntity<>(complaintCrudDTOs, HttpStatus.OK);
    }
}
