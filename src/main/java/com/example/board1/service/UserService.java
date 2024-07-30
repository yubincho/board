package com.example.board1.service;


import com.example.board1.entity.User;
import com.example.board1.exception.NotFoundException;
import com.example.board1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    public User findUserByUserId(String userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

}
