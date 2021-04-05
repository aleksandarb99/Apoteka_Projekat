package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.enums.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    List<MyUser> findAllByUserType(UserType userType);
}
