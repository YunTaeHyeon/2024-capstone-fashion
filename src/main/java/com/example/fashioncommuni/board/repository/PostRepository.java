package com.example.fashioncommuni.board.repository;

import com.example.fashioncommuni.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> { // 게시물 레포지토리
    List<Post> findByUserId(Long user_id); // 사용자 ID로 게시물 조회
    List<Post> findByCategoryId(Long category_id); // 카테고리 ID로 게시물 조회
    Page<Post> findByTitle(String keyword, Pageable pageable); // 제목으로 게시물 조회
}
