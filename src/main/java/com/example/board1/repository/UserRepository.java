package com.example.board1.repository;

import com.example.board1.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

//    Optional<User> findById(String userId);

}
