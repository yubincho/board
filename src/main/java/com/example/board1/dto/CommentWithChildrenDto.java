package com.example.board1.dto;

import com.example.board1.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentWithChildrenDto {

    private Long commentId;
    private Long postId;
    private String content;
    private String nickname;  // user
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;
    private List<CommentResponseDto> children; // 자식 댓글 리스트 추가

    public static CommentWithChildrenDto toDto(Comment comment) {
        String nickname = comment.getUser().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = comment.getUser().getUserId();
        }

        // 자식 댓글 리스트 변환, null인 경우 빈 리스트 반환
        List<CommentResponseDto> childDtos = comment.getChildren() == null
                ? Collections.emptyList()
                : comment.getChildren().stream()
                .map(CommentResponseDto::toDto)
                .collect(Collectors.toList());

        return CommentWithChildrenDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .content(comment.getContent())
                .userId(comment.getUser().getUserId())
                .createdAt(comment.getCreatedAt())
                .updatedAt(comment.getModifiedAt())
                .nickname(nickname)
                .children(childDtos) // 자식 댓글 리스트 추가
                .build();
    }

}
