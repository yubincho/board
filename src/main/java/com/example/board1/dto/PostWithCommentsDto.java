package com.example.board1.dto;

import com.example.board1.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostWithCommentsDto {

    private Long postId;
    private String title;
    private String content;
    private Long greats;
    private String nickname;  // user
    private List<CommentWithChildrenDto> commentList;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userId;


    public static PostWithCommentsDto toDto(Post post) {
        String nickname = post.getUser().getNickname();
        if (nickname == null || nickname.isBlank()) {
            nickname = post.getUser().getUserId();
        }

        return PostWithCommentsDto.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .greats(post.getLikesCount())   // 좋아요 수
                .commentList(post.getComments()
                        .stream()
                        .map(CommentWithChildrenDto::toDto)
                        .collect(Collectors.toList())
                )
                .userId(post.getUser().getUserId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getModifiedAt())
                .build();
    }

}


