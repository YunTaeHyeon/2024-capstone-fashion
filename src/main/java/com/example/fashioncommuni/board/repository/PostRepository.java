package com.example.fashioncommuni.board.repository;

import com.example.fashioncommuni.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> { // 게시물 레포지토리
    List<Post> findByUserId(Long userId); // 사용자 ID로 게시물 조회
    List<Post> findByCategoryId(Long categoryId); // 카테고리 ID로 게시물 조회
}
