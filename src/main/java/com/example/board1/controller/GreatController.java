package com.example.board1.controller;

import com.example.board1.dto.GreatForPostRequestDto;
import com.example.board1.dto.GreatForPostResponseDto;
import com.example.board1.service.GreatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GreatController {

    private final GreatService greatService;


    // 좋아요 또는 취소
    // '메시지' & '좋아요' 개수 반환
    @PostMapping("/greats")
    public ResponseEntity<?> createOrDeleteGreat(@RequestBody GreatForPostRequestDto dto) {
        Boolean isLiked = greatService.likePost(dto.getPostId(), dto.getUserId());
        Long greatCount = greatService.countGreatByPostId(dto.getPostId());
        System.out.println("##" + greatCount); //
        GreatForPostResponseDto responseDto = GreatForPostResponseDto.toDto(dto.getPostId(), greatCount);
        String message = isLiked ? "좋아요가 완료 되었습니다." : "좋아요가 취소 되었습니다.";

        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("great", responseDto);
        return ResponseEntity.ok().body(response);
    }






}
