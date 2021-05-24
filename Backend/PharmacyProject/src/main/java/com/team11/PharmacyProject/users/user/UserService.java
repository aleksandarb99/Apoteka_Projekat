package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.user.UserUpdateDTO;

import java.util.List;

public interface UserService {

    MyUser findOne(Long id);
    MyUser updateUser(UserUpdateDTO user);
    boolean insertUser(MyUser user);

    List<MyUser> getUsersByUserType(String type);

    boolean delete(long id);

    boolean changePassword(Long userId, String oldPassword, String newPassword);

    boolean setPassword(Long userId, String newPassword);
}
