package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.UserDTO;
import com.team11.PharmacyProject.enums.UserType;

import java.util.List;

public interface UserService {

    MyUser findOne(Long id);
    MyUser updateUser(UserDTO user);
    boolean insertUser(MyUser user);
    List<MyUser> getUsersByUserType(UserType type);
}
