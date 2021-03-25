package com.team11.PharmacyProject.users.user;

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
}
