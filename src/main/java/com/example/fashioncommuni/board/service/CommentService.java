package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.DTO.comment.CommentRequestDTO;
import com.example.fashioncommuni.board.DTO.comment.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    /**
     * 댓글 작성
     * @param commentRequestDTO 댓글 정보
     * @param postId 게시물
     * @param email 작성자
     * @return 댓글 ID
     */
    Long writeComment(CommentRequestDTO commentRequestDTO, Long postId, String email);

    /**
     * 댓글 조회
     * @param comment_id 게시물
     * @return 게시물 별 댓글
     */
    List<CommentResponseDTO> commentList(Long comment_id);

    /**
     * 댓글 수정
     * @param commentRequestDTO 댓글 정보
     * @param comment_id 댓글 ID
     */
    void updateComment(CommentRequestDTO commentRequestDTO, Long comment_id);

    /**
     * 댓글 삭제
     * @param comment_id 댓글 ID
     */
    void deleteComment(Long comment_id);
}
