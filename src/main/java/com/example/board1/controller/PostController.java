package com.example.board1.controller;

import com.example.board1.dto.PostRequestDto;
import com.example.board1.dto.PostResponseDto;
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


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto dto) {
        try {
            Optional<User> userOptional = userRepository.findById(dto.getUserId());
            if (userOptional.isEmpty()) {
                return ResponseEntity.badRequest().body("User not found");
            }
            User user = userOptional.get();

            Post newPost = postService.createPost(dto, user);
            PostResponseDto responseDto = PostResponseDto.toDto(newPost);

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Post created successfully");
            response.put("post", responseDto);
            return ResponseEntity.ok().body(response);
        } catch (InvalidRequestException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("message", "유저 또는 입력 데이터가 유효하지 않습니다."));
        } catch (Exception e) {
            // 로깅 등의 추가적인 오류 처리를 고려
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "Internal server error"));
        }

    }


    @GetMapping("/posts")
    public ResponseEntity<?> findAllPosts() {
        List<PostResponseDto> postResponseDtos = postService.findAll();
        return ResponseEntity.ok().body(postResponseDtos);
    }


    @GetMapping("/posts/{postId}")
    public ResponseEntity<?> findPostByPostId(@PathVariable Long postId) {
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


    @PostMapping("/posts/{postId}/update")
    public ResponseEntity<?> updatePost(@PathVariable Long postId, @RequestBody PostRequestDto dto) {

        try {
            Post updatedPost = postService.update(dto, postId);
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
    public ResponseEntity<?> deletePostByPostId(@PathVariable Long postId) {
        try {
            postService.deletePostByPostId(postId);
            return ResponseEntity.ok().body(Collections.singletonMap("message", "게시글이 성공적으로 삭제되었습니다."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("message", e.getMessage()));
        } catch (Exception e) {
            // 기타 예외 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", e.getMessage()));
        }
    }



}
