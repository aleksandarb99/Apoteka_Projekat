package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public MyUser findOne(Long id) {
        Optional<MyUser> user = userRepository.findById(id);
        return user.orElse(null);
    }

    @Override
    public MyUser updateUser(UserDTO user) {

        Optional<MyUser> dbUser = userRepository.findById(user.getId());
        if(dbUser.isPresent()) {
            MyUser updatedUser = dbUser.get();
            updatedUser.setPassword(user.getPassword());
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setTelephone(user.getTelephone());
            updatedUser.setAddress(user.getAddress());
            userRepository.save(updatedUser);
            return updatedUser;
        }
        return null;
    }
}
