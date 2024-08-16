package com.example.board1.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PasswordEncoderTest {

    public static void main(String[] args) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("password");
        System.out.println(encodedPassword);
        // $2a$10$0iDJUiHxUsLefyLugXRJ7uStPUin1f2QIX2bFHn9rT7bOLGWIST3u


    }
}
