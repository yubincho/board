package com.example.board1.controller;

import com.example.board1.dto.CommentRequestDto;
import com.example.board1.dto.CommentResponseDto;
import com.example.board1.entity.Comment;
import com.example.board1.exception.InvalidRequestException;
import com.example.board1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/comments")
    public ResponseEntity<?> findAllComments() {
        List<CommentResponseDto> comments = commentService.findAll();
        return ResponseEntity.ok().body(comments);
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/comments")
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto dto) {

        try {
            Comment comment = commentService.createComment(dto);
            CommentResponseDto newComment = CommentResponseDto.toDto(comment);
            Map response = new HashMap();
            response.put("message","댓글이 성공적으로 작성되었습니다.");
            response.put("newComment", newComment);
            return ResponseEntity.ok().body(response);
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "유저 또는 입력 데이터가 유효하지 않습니다."));
        } catch (Exception e) {
            // 로깅 등의 추가적인 오류 처리를 고려
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Internal server error"));
        }

    }


    @GetMapping("/comments/{commentId}")
    public ResponseEntity<?> findByCommentId(@PathVariable Long commentId) {
        Comment comment = commentService.findByCommentId(commentId);
        CommentResponseDto responseDto = CommentResponseDto.toDto(comment);
        return ResponseEntity.ok().body(responseDto);
    }


    @PostMapping("/comments/{commentId}/update")
    public ResponseEntity<?> updateCommentById(@PathVariable Long commentId, @RequestBody CommentRequestDto dto) {
        try {
            Comment comment = commentService.updateCommentById(commentId, dto);
            CommentResponseDto responseDto = CommentResponseDto.toDto(comment);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Comment updated successfully");
            response.put("comment", responseDto);
            return ResponseEntity.ok().body(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }


    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<?> deleteByCommentId(@PathVariable Long commentId) {
        try {
            commentService.deleteById(commentId);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "게시글이 성공적으로 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }




}
