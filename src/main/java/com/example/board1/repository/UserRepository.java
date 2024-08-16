package com.example.board1.repository;

import com.example.board1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

//    Optional<User> findById(String userId);

    Optional<User> findByEmailAndFromSocial(String email, boolean social);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roleSet WHERE u.email = :email")
    Optional<User> findByEmailWithRoles(String email);

    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

}
