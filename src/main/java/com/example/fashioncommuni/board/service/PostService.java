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

    // 게시물 삭제 기능 구현
    public void deletePost(Long postId) {
        // postId를 이용하여 해당 게시물을 찾습니다.
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId));
        // 데이터베이스에서 게시물을 삭제합니다.
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

        // toDO: 현재 시간으로 수정 시간 업데이트 나중에

        // 업데이트된 게시물 저장 후 반환
        return postRepository.save(existingPost); // 저장 후 리턴
    }
}