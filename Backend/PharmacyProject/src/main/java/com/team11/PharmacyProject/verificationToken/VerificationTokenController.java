package com.team11.PharmacyProject.verificationToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "verification")
public class VerificationTokenController {

    @Autowired
    VerificationTokenService verificationTokenService;

    @GetMapping()
    public ResponseEntity<?> verify(@RequestParam String token) {
        try {
            verificationTokenService.verify(token);
            return new ResponseEntity<>("Verified", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }
}
