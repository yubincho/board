package com.example.board1.repository;

import com.example.board1.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @Test
    void 이메일_찾기() {
        Optional<User> result = userRepository.findByEmail("user1@example.com");

        if (result.isPresent()) {
            User user = result.get();
            System.out.println(user);
        }
    }

    @Transactional
    @Test
    void 이메일과소셜로_찾기() {
        Optional<User> result = userRepository.findByEmailAndFromSocial("user3@example.com", false);
        if (result.isPresent()) {
            System.out.println(result.get());
        }
    }



}