package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.UserDTO;

public interface UserService {

    MyUser findOne(Long id);
    MyUser updateUser(UserDTO user);
}
