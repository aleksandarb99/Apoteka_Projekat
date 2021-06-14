package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.user.UserUpdateDTO;
import com.team11.PharmacyProject.email.EmailService;
import com.team11.PharmacyProject.exceptions.CustomException;
import com.team11.PharmacyProject.verificationToken.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private VerificationTokenService verificationTokenService;
    @Autowired
    private EmailService emailService;

    @Override
    public MyUser findOne(Long id) {
        Slice<MyUser> user = userRepository.findByIdFetchAddress(id, PageRequest.of(0, 1));
        if (user.hasContent()) {
            return user.getContent().get(0);
        }
        return null;
    }

    @Override
    public MyUser updateUser(UserUpdateDTO user) {

        Optional<MyUser> dbUser = userRepository.findById(user.getId());
        if (dbUser.isEmpty()) throw new RuntimeException("User is not recognized in the database!");

        MyUser updatedUser = dbUser.get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setTelephone(user.getTelephone());
        updatedUser.setAddress(user.getAddress());
        userRepository.save(updatedUser);
        return updatedUser;
    }

    @Override
    public void insertUser(MyUser user) throws Exception {
        if (user == null) {
            throw new CustomException("Oops");
        }
        if (user.getFirstName().isBlank()) {
            throw new CustomException("First name cannot be blank");
        }

        if (user.getLastName().isBlank()) {
            throw new CustomException("Last name cannot be blank");
        }

        Optional<MyUser> userOptional = userRepository.findByEmail(user.getEmail());
        if (userOptional.isPresent()) {
            throw new CustomException("An account with this email already exists");
        }

        String encoded = passwordEncoder.encode(user.getPassword());
        user.setPassword(encoded);

        String token = UUID.randomUUID().toString();
        if (!user.getRole().getName().equals("PATIENT")) {
            user.setEmailVerified(true);
            userRepository.save(user);
        } else {
            userRepository.save(user);
            verificationTokenService.createVerificationToken(user, token);
            emailService.sendVerificationEmail(user, token);
        }
    }

    @Override
    public List<MyUser> getUsersByUserType(String type) {
        return userRepository.findAllByUserType(type);
    }

    @Override
    public boolean delete(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean changePassword(Long userId, String oldPassword, String newPassword) throws CustomException {
        if (newPassword.length() < 6) {
            throw new CustomException("Error!");
        }
        MyUser currentUser = userRepository.findFirstById(userId);
        String newPasswordHash = passwordEncoder.encode(newPassword);
        if (!passwordEncoder.matches(oldPassword, currentUser.getPassword())) {
            return false;
        } else {
            currentUser.setPassword(newPasswordHash);
            userRepository.save(currentUser);
            return true;
        }
    }

    @Override
    public boolean setPassword(Long userId, String newPassword) {
        MyUser currentUser = userRepository.findFirstById(userId);
        String newPasswordHash = passwordEncoder.encode(newPassword);
        // You can set password only once (first login)
        if (currentUser.isPasswordChanged()) {
            return false;
        } else {
            currentUser.setPassword(newPasswordHash);
            currentUser.setPasswordChanged(true);
            userRepository.save(currentUser);
            return true;
        }
    }
}
