package com.example.board1.dto;


import com.example.board1.entity.Great;
import com.example.board1.entity.Post;
import com.example.board1.entity.User;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PostRequestDto {

    private String title;
    private String content;
//    private String userId;


    public static Post toEntity(PostRequestDto dto, User user) {
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .user(user)
                .build();
    }

}
