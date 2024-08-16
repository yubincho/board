package com.example.board1.dto.userSecurity;

import com.example.board1.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


@Getter
@Setter
@ToString
public class UserDto {

    private String email;
    private String name;

    private boolean fromSocial;


}
