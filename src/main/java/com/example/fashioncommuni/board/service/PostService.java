package com.example.board.service;

import com.example.board.domain.Post;
import com.example.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository; // PostRepository 의존성 주입

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now()); // 현재 시간을 설정
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