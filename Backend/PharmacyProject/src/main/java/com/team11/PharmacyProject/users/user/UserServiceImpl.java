package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.UserDTO;
import com.team11.PharmacyProject.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public MyUser findOne(Long id) {
        Optional<MyUser> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public MyUser updateUser(UserDTO user) {

        // TODO check when DTO attributes are null
        Optional<MyUser> dbUser = userRepository.findById(user.getId());
        if(dbUser.isPresent()) {
            MyUser updatedUser = dbUser.get();
            if (user.getPassword() != null)
                updatedUser.setPassword(user.getPassword());
            if (user.getFirstName() != null)
                updatedUser.setFirstName(user.getFirstName());
            if (user.getLastName() != null)
                updatedUser.setLastName(user.getLastName());
            if (user.getTelephone() != null)
                updatedUser.setTelephone(user.getTelephone());
            if (user.getAddress() != null)
                updatedUser.setAddress(user.getAddress());
            userRepository.save(updatedUser);
            return updatedUser;
        }
        return null;
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
    public List<MyUser> getUsersByUserType(UserType type) {
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
}
