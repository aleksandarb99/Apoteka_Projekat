package com.team11.PharmacyProject.verificationToken;

import com.team11.PharmacyProject.users.user.MyUser;
import com.team11.PharmacyProject.users.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService {

    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private UserRepository userRepository;

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

        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            throw new Exception("Your token has expired!");
        }

        user.setEmailVerified(true);
        userRepository.save(user);
    }

    @Override
    public void createVerificationToken(MyUser user, String token) {
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(user);
        verificationTokenRepository.save(verificationToken);
    }
}
