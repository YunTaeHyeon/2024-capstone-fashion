package com.example.fashioncommuni.board.repository;

import com.example.fashioncommuni.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);
    List<Post> findByCategoryId(Long categoryId);
}
