package com.team11.PharmacyProject.verificationToken;

import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.users.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    VerificationTokenRepository verificationTokenRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public void verify(String token) throws Exception {
        Optional<VerificationToken> verificationTokenOpt = verificationTokenRepository.findFirstByToken(token);
        if (verificationTokenOpt.isEmpty()) {
            throw new Exception("Invalid verification token!");
        }
        VerificationToken verificationToken = verificationTokenOpt.get();
        MyUser user = verificationToken.getUser();
        if (user.isEmailVerified()) {
            throw new Exception("E-Mail has already been verified!");
        }
        user.setEmailVerified(true);
        userRepository.save(user);
    }
}
