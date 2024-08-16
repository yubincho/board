package com.example.board1.dto;

import com.example.board1.entity.Great;
import lombok.*;

import java.util.List;


@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GreatForPostAndUserDto {

    private Long postId;
    private Long greatsCount;
    private String userId;


    public static GreatForPostAndUserDto toDto(Long postId, String userId, Long greatsCount) {

        return GreatForPostAndUserDto.builder()
                .postId(postId)
                .greatsCount(greatsCount)
                .userId(userId)
                .build();
    }




}
