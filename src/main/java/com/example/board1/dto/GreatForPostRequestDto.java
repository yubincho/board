package com.example.board1.dto;


import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GreatForPostRequestDto {

    private Long postId;
    private String userId;


}
