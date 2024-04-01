package com.example.fashioncommuni.board.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "posts")
public class Post { // toDO: set이라고 쓴 애들 이름 잘 짓기.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 생성
    private Long postId; // 게시글 식별자

    private String title; // 제목

    @Column(columnDefinition = "TEXT") // TEXT 타입
    private String body; // 내용

    @Column(name = "user_id")  // user_id 컬럼
    private Long userId; // 사용자 식별자

    @Column(name = "category_id") // category_id 컬럼
    private Long categoryId; // 카테고리 식별자

    private String status; // 상태

    @Column(name = "created_at")
    private LocalDateTime createdAt; // 생성 시간

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
