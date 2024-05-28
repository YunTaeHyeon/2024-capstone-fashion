package com.example.fashioncommuni.board.repository;

import com.example.fashioncommuni.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> { // 게시물 레포지토리

    Page<Post> findByTitle(String keyword, Pageable pageable); // 제목으로 게시물 조회
    Page<Post> findByCategoryId(Long category_id, Pageable pageable); // 카테고리로 게시물 조회
    Page<Post> findByPostIdIn(List<Long> postIds, Pageable pageable); // 추천 시스템에서 사용자 아이디로 게시물 조회
}
