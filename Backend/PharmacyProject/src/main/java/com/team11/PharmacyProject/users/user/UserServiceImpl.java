package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.user.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MyUser findOne(Long id) {
        Slice<MyUser> user = userRepository.findByIdFetchAdderss(id, PageRequest.of(0, 1));
        if (user.hasContent()) {
            return user.getContent().get(0);
        }
        return null;
    }

    @Override
    public MyUser updateUser(UserUpdateDTO user) {

        Optional<MyUser> dbUser = userRepository.findById(user.getId());
        if(dbUser.isEmpty()) throw new RuntimeException("User is not recognized in the database!");

        MyUser updatedUser = dbUser.get();
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setTelephone(user.getTelephone());
        updatedUser.setAddress(user.getAddress());
        userRepository.save(updatedUser);
        return updatedUser;
    }

    @Override
    public boolean insertUser(MyUser user) {
        if (user != null) {
            String encoded = passwordEncoder.encode(user.getPassword());
            user.setPassword(encoded);
            userRepository.save(user);
            return true;
        } else {
            return false;
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
    public boolean changePassword(Long userId, String oldPassword, String newPassword) {
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
