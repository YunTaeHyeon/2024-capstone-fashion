package com.example.board.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {
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

    // Getter and setters
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
