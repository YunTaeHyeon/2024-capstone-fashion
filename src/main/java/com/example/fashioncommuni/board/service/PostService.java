package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.repository.PostRepository;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    private final PostRepository postRepository; // PostRepository 의존성 주입

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now()); // 현재 시간을 설정 toDo: set은 발작버튼입니다.
        return postRepository.save(post); // 저장 후 리턴
    }

    public Post getPost(Long postId) {
        return postRepository.findById(postId).orElse(null); // postId로 조회
    }

    public Post updatePost(Post post) {
        post.setCreatedAt(LocalDateTime.now()); // 현재 시간을 설정
        return postRepository.save(post); // 저장 후 리턴
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId); // postId로 삭제
    }

    public List<Post> getPosts() {
        return postRepository.findAll(); // 모든 게시글 조회
    }

    public List<Post> getPostsByUserId(Long userId) {
        return postRepository.findByUserId(userId); // userId로 조회
    }

    public List<Post> getPostsByCategoryId(Long categoryId) {
        return postRepository.findByCategoryId(categoryId); // categoryId로 조회
    }

    public Post updatePost(Long postId, Post updatedPost) { // 게시물 수정
        // 기존 게시물 조회
        Post existingPost = getPost(postId); // 게시물 조회
        if (existingPost == null) { // 게시물이 없으면
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId); // 예외 발생
        }

        // 수정된 정보로 게시물 업데이트
        existingPost.setTitle(updatedPost.getTitle()); // 제목 업데이트
        existingPost.setBody(updatedPost.getBody()); // 내용 업데이트
        // 필요한 경우 다른 속성도 업데이트할 수 있음

        // 업데이트된 게시물 저장 후 반환
        return postRepository.save(existingPost); // 저장 후 리턴
    }

    public void validatePost(Post post) { // 게시글 유효성 검사
        if (post.getTitle() == null || post.getTitle().isEmpty()) { // 제목이 없으면
            throw new IllegalArgumentException("제목은 필수 입력값입니다."); // 예외 발생
        }
        if (post.getBody() == null || post.getBody().isEmpty()) { // 내용이 없으면
            throw new IllegalArgumentException("내용은 필수 입력값입니다."); // 예외 발생
        }
        // 유효성 검사 로직 추가 가능
    }
}