package com.example.board1.repository;

import com.example.board1.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  PostRepository extends JpaRepository<Post, Long> {


}
