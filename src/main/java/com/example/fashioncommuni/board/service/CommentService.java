package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.domain.Comment;

public interface CommentService {// 댓글 서비스
    Comment createComment(Long postId, Comment comment);// 댓글 생성
}
