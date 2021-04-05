package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.UserUpdateDTO;
import com.team11.PharmacyProject.enums.UserType;

import java.util.List;

public interface UserService {

    MyUser findOne(Long id);
    MyUser updateUser(UserUpdateDTO user);
    boolean insertUser(MyUser user);

    List<MyUser> getUsersByUserType(UserType type);

    boolean delete(long id);
}
