package com.team11.PharmacyProject.rating;

import com.team11.PharmacyProject.dto.rating.RatingCreateUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/rating")
public class RatingController {

    @Autowired
    RatingService ratingService;

    @GetMapping(value = "/dermatologist/{dId}/patient/{pId}/grade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getDermatologistGrade(@PathVariable("dId") Long dId, @PathVariable("pId") Long pId){
        Rating grade = ratingService.getDermatologistGrade(dId, pId);

        if (grade == null) {
            return new ResponseEntity<>("not graded", HttpStatus.OK);
        }

        return new ResponseEntity<>(new RatingCreateUpdateDTO(grade), HttpStatus.OK);
    }

//    @PostMapping(value = "/rejectrequest/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> rejectRequest(@PathVariable("requestId") String requestId, @RequestBody String reason){
//        boolean flag = requestForHolidayService.rejectRequest(requestId, reason);
//        if(!flag){
//            return new ResponseEntity<>("Failed to reject", HttpStatus.BAD_REQUEST);
//        }
//        return new ResponseEntity<>("Successfully rejected", HttpStatus.OK);
//    }
}
