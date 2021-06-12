package com.team11.PharmacyProject.verificationToken;

import com.team11.PharmacyProject.users.user.MyUser;

public interface VerificationTokenService {
    void verify(String token) throws Exception;

    void createVerificationToken(MyUser user, String token);
}
