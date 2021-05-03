package com.team11.PharmacyProject.complaint;

import com.team11.PharmacyProject.dto.complaint.ComplaintCrudDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
