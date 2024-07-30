package com.example.board1.dto;


import com.example.board1.entity.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private Long greats;
    private String nickname;  // user
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;


    public static PostResponseDto toDto(Post post) {
        String nickname = post.getUser().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = post.getUser().getUserId();
        }

        return PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .greats(post.getLikesCount())
                .userId(post.getUser().getUserId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getModifiedAt())
                .build();
    }

}
