package com.team11.PharmacyProject.verificationToken;

import com.team11.PharmacyProject.users.user.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findFirstByToken(String token);


}
