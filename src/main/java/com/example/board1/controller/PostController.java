package com.example.board1.controller;

import com.example.board1.dto.PostRequestDto;
import com.example.board1.dto.PostResponseDto;
import com.example.board1.dto.PostWithCommentsDto;
import com.example.board1.entity.Post;
import com.example.board1.entity.User;
import com.example.board1.exception.InvalidRequestException;
import com.example.board1.exception.NotFoundException;
import com.example.board1.repository.UserRepository;
import com.example.board1.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;


    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto dto, @RequestParam String userId) {
        try {
            Post newPost = postService.createPost(dto, userId);
            PostResponseDto responseDto = PostResponseDto.toDto(newPost);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Post created successfully");
            response.put("post", responseDto);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (InvalidRequestException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "유저 또는 입력 데이터가 유효하지 않습니다."));
        } catch (Exception e) {
            // 로깅 등의 추가적인 오류 처리를 고려
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Internal server error"));
        }

    }


    @GetMapping("/posts")
    public ResponseEntity<?> findAllPosts() {
        List<PostResponseDto> postResponseDtos = postService.findAll();
        return ResponseEntity.ok().body(postResponseDtos);
    }


    // postId를 받아 post 만 출력
    @GetMapping("/posts/post/{postId}")
    public ResponseEntity<?> findPostByPostId(@PathVariable Long postId) {
        // @RequestParam String userId 삭제 - h2 db 때문.
        try {
            Post findPOst = postService.findPostByPostId(postId);
            PostResponseDto responseDto = PostResponseDto.toDto(findPOst);
            return ResponseEntity.ok().body(responseDto);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Post not found"));
        } catch (Exception e) {
            // 로깅 등의 추가적인 오류 처리를 고려
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Internal server error"));
        }
    }


    // postId를 받아 post 와 comments 모두 출력
    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> findPostWithCommentsByPostId(@PathVariable Long postId) {
        // @RequestParam String userId 삭제 - h2 db 때문.
        try {
            PostWithCommentsDto responseDto = postService.findPostWithCommentsByPostId(postId);
            return ResponseEntity.ok().body(responseDto);
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "Post not found"));
        } catch (Exception e) {
            // 로깅 등의 추가적인 오류 처리를 고려
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Internal server error"));
        }
    }


    @PostMapping("/posts/{postId}/update")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto dto, @RequestParam String userId) {

        try {
            Post updatedPost = postService.update(dto, postId, userId);
            PostResponseDto responseDto = PostResponseDto.toDto(updatedPost);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Post updated successfully");
            response.put("post", responseDto);
            return ResponseEntity.ok().body(response);
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "유저 또는 입력 데이터가 유효하지 않습니다."));
        } catch (Exception e) {
            // 로깅 등의 추가적인 오류 처리를 고려
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Internal server error"));
        }
    }



    @DeleteMapping("/posts/{postId}/delete")
    public ResponseEntity<?> deletePostByPostId(@PathVariable Long postId, @RequestParam String userId) {
        try {
            postService.deletePostByPostId(postId, userId);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "게시글이 성공적으로 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }



}
