package com.team11.PharmacyProject.verificationToken;

public interface VerificationTokenService {
    void verify(String token) throws Exception;
}
