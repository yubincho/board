package com.example.board1.dto;


import com.example.board1.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;

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

        long greatsCount = post.getLikesCount(); // 수정: Optional 사용 제거

        return PostResponseDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .greats(post.getLikesCount())   // 좋아요 수
                .userId(post.getUser().getUserId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getModifiedAt())
                .build();
    }

}
