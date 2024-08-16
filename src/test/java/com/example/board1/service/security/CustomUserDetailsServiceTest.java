package com.example.board1.service.security;

import com.example.board1.entity.User;
import com.example.board1.entity.constant.UserRole;
import com.example.board1.entity.userDetails.CustomUserDetails;
import com.example.board1.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordEncoder = new BCryptPasswordEncoder();
    }


    @Test
    void testPasswordEncoding() {
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }

    @Test
    void testLoadUserByUsername_Success() {
        String email = "user1@example.com";
        String rawPassword = "password";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .userId("user1") // userId 추가
                .email(email)
                .userPassword(encodedPassword)
                .roleSet(Set.of(UserRole.USER))
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        System.out.println("[# user]" + user);
        System.out.println("[userRepository.findByEmailWithRoles(email)]" + userRepository.findByEmail(email));

        CustomUserDetails userDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);

        System.out.println("[userDetails]" + userDetails);
//        assertNotNull(userDetails);
//        assertEquals(email, userDetails.getUsername());
        assertTrue(passwordEncoder.matches(rawPassword, userDetails.getPassword()));
//        assertFalse(userDetails.getAuthorities().isEmpty());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        String email = "user1@example.com";

        when(userRepository.findByEmailWithRoles(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> customUserDetailsService.loadUserByUsername(email));
    }

}