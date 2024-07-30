package com.example.board1.service;

import com.example.board1.dto.CommentRequestDto;
import com.example.board1.dto.CommentResponseDto;
import com.example.board1.entity.Comment;
import com.example.board1.entity.Post;
import com.example.board1.entity.User;
import com.example.board1.exception.NotFoundException;
import com.example.board1.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;


    public List<CommentResponseDto> findAll() {

        List<Comment> comments = commentRepository.findAll();

        return comments.stream()
                .map(CommentResponseDto::toDto)
                .collect(Collectors.toList());
    }


    @Transactional
    public Comment createComment(CommentRequestDto dto, String userId) {
        User user = userService.findUserByUserId(userId);
        Post post = postService.findPostByPostId(dto.getPostId());

        Comment comment = this.toEntity(dto, userId);

        return commentRepository.save(comment);
    }

    public Comment toEntity(CommentRequestDto dto, String userId) {
        Post post = postService.findPostByPostId(dto.getPostId());
        User user = userService.findUserByUserId(userId);

        return Comment.builder()
                .content(dto.getContent())
                .post(post)
                .user(user)
                .build();
    }


    public Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException("Comment not found.")
        );
    }


    @Transactional
    public Comment updateCommentById(Long commentId, CommentRequestDto dto, String userId) {
        Comment comment = this.findByCommentId(commentId);

        User user = userService.findUserByUserId(userId);
        User commentUser = this.findByCommentId(commentId).getUser();
        if (!user.equals(commentUser)) {
            throw new IllegalArgumentException("User not found for given userId");
        }

        comment.setContent(dto.getContent());
        return commentRepository.save(comment);
    }


    @Transactional
    public void deleteById(Long commentId, String userId) {
        User user = userService.findUserByUserId(userId);
        Comment comment = this.findByCommentId(commentId);

        if(!user.equals(comment.getUser())) {
            throw new IllegalArgumentException("User not found for given userId");
        }
        commentRepository.delete(comment);
    }

}
