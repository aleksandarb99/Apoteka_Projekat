package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.dto.user.UserUpdateDTO;
import com.team11.PharmacyProject.exceptions.CustomException;

import java.util.List;

public interface UserService {

    MyUser findOne(Long id);

    MyUser updateUser(UserUpdateDTO user);

    void insertUser(MyUser user) throws Exception;

    List<MyUser> getUsersByUserType(String type);

    boolean delete(long id);

    boolean changePassword(Long userId, String oldPassword, String newPassword) throws CustomException;

    boolean setPassword(Long userId, String newPassword);
}
