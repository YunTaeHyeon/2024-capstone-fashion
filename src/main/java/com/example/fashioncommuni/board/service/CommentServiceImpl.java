package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.domain.Comment;
import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.repository.CommentRepository;
import com.example.fashioncommuni.board.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentServiceImpl implements CommentService { // 댓글 서비스 구현체

    private final CommentRepository commentRepository; // 댓글 레포지토리
    private final PostRepository postRepository; // 게시물 레포지토리

    @Autowired // 생성자 주입
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository; // 댓글 레포지토리 주입
        this.postRepository = postRepository; // 게시물 레포지토리 주입
    }

    @Override
    public Comment createComment(Long postId, Comment comment) { // 댓글 생성
        // 해당 게시물이 존재하는지 확인
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId));

        // 댓글 정보 설정
        comment.setPost(post);
        comment.setCreatedAt(LocalDateTime.now());

        // 댓글 저장
        return commentRepository.save(comment);
    }
}
