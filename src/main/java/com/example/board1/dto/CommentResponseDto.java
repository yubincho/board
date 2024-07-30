package com.example.board1.dto;

import com.example.board1.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponseDto {

    private Long commentId;
    private Long postId;
    private String content;
    private String nickname;  // user
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;


    public static CommentResponseDto toDto(Comment comment) {
        String nickname = comment.getUser().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = comment.getUser().getUserId();
        }

        return CommentResponseDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .userId(comment.getUser().getUserId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getModifiedAt())
                .nickname(comment.getUser().getNickname())
                .build();


    }



}
