package com.example.board1.entity.userDetails;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Slf4j
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {

    private String userId;

    private String email;  // username

    private String password;

    private Collection<? extends GrantedAuthority> authorities; // 사용자에게 부여된 권한 목록

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
//        return authorities.stream()
//                .map(i -> new SimpleGrantedAuthority(userId)).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    } // 계정이 만료되지 않았는지 확인하는 메서드 (여기서는 항상 true를 반환합니다.)

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
