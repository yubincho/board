package com.example.board1.repository;

import com.example.board1.entity.Great;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GreatRepository extends JpaRepository<Great, Long> {

    @Query("SELECT g FROM Great g WHERE g.post.id = :postId AND g.user.userId = :userId")
    Great findByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") String userId);

    @Query("SELECT COUNT(g) FROM Great g WHERE g.post.id = :postId")
    Long countGreatByPostId(Long postId);
}
