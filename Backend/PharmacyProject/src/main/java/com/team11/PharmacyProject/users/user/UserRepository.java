package com.team11.PharmacyProject.users.user;

import com.team11.PharmacyProject.enums.UserType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<MyUser, Long> {
    @Query("select user from MyUser user join fetch user.address")
    List<MyUser> findAllByUserType(UserType userType);

    @Query("select user from MyUser user join fetch user.address where user.id = ?1")
    Slice<MyUser> findByIdFetchAdderss(long id, Pageable pg);

    MyUser findByEmail(String username);
    MyUser findFirstById(Long id);
}
