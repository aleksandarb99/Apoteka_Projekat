package com.team11.PharmacyProject.rating;

import com.team11.PharmacyProject.dto.medicine.MedicineCrudDTO;
import com.team11.PharmacyProject.dto.rating.RatingCreateUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @GetMapping(value = "/pharmacist/{pId}/patient/{paId}/grade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPharmacistGrade(@PathVariable("pId") Long pId, @PathVariable("paId") Long paId){
        Rating grade = ratingService.getPharmacistGrade(pId, paId);

        if (grade == null) {
            return new ResponseEntity<>("not graded", HttpStatus.OK);
        }

        return new ResponseEntity<>(new RatingCreateUpdateDTO(grade), HttpStatus.OK);
    }

    @GetMapping(value = "/medicine/{mId}/patient/{paId}/grade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getMedicineGrade(@PathVariable("mId") Long mId, @PathVariable("paId") Long paId){
        Rating grade = ratingService.getMedicineGrade(mId, paId);

        if (grade == null) {
            return new ResponseEntity<>("not graded", HttpStatus.OK);
        }

        return new ResponseEntity<>(new RatingCreateUpdateDTO(grade), HttpStatus.OK);
    }

    @GetMapping(value = "/pharmacy/{pId}/patient/{paId}/grade", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getPharmacyGrade(@PathVariable("pId") Long pId, @PathVariable("paId") Long paId){
        Rating grade = ratingService.getPharmacyGrade(pId, paId);

        if (grade == null) {
            return new ResponseEntity<>("not graded", HttpStatus.OK);
        }

        return new ResponseEntity<>(new RatingCreateUpdateDTO(grade), HttpStatus.OK);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addRating(@Valid @RequestBody RatingCreateUpdateDTO dto, BindingResult result){

        if (result.hasErrors()) {
            return new ResponseEntity<>("Sent data is not valid!", HttpStatus.BAD_REQUEST);
        }

        try {
            ratingService.addRating(dto);
        }catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("The grade is recorded successfully!", HttpStatus.OK);
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> editRating(@Valid @RequestBody RatingCreateUpdateDTO dto, BindingResult result){

        if (result.hasErrors()) {
            return new ResponseEntity<>("Sent data is not valid!", HttpStatus.BAD_REQUEST);
        }

        try {
            ratingService.editRating(dto);
        }catch(Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("The grade is updated successfully!", HttpStatus.OK);
    }
}
