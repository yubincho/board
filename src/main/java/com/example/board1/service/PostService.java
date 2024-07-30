package com.example.board1.service;

import com.example.board1.dto.PostRequestDto;
import com.example.board1.dto.PostResponseDto;
import com.example.board1.entity.Post;
import com.example.board1.entity.User;
import com.example.board1.exception.InvalidRequestException;
import com.example.board1.exception.NotFoundException;
import com.example.board1.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;


    @Transactional
    public Post createPost(PostRequestDto dto, String userId) {
        User user = userService.findUserByUserId(userId);
        Post newPost = PostRequestDto.toEntity(dto, user);
        return postRepository.save(newPost);

    }


    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(PostResponseDto::toDto)
                .collect(Collectors.toList());
    }


    public Post findPostByPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException("Post not found")
        );
    }


    @Transactional
    public Post update(PostRequestDto dto, Long postId, String userId) {
        User user = userService.findUserByUserId(userId);
        User postUser = this.findPostByPostId(postId).getUser();
        if (!user.equals(postUser)) {
            throw new IllegalArgumentException("User not found for given userId");
        }
        Post findPost = this.findPostByPostId(postId);
        findPost.setTitle(dto.getTitle());
        findPost.setContent(dto.getContent());
        return postRepository.save(findPost);
    }



    @Transactional
    public void deletePostByPostId(Long postId, String userId) {
        User user = userService.findUserByUserId(userId);
        Post post = this.findPostByPostId(postId);

        if (!user.equals(post.getUser())) {
            throw new IllegalArgumentException("User not found for given userId");
        }
        postRepository.delete(post);
    }


//    public int countLikes(Long postId) {
//        Post post = this.findPostByPostId(postId);
//        return post.getLikesCount();
//    }





}
