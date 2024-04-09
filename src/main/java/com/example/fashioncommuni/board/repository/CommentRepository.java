package com.example.fashioncommuni.board.repository;

import com.example.fashioncommuni.board.domain.Comment;
import com.example.fashioncommuni.board.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * Board로 Comment 조회
     * @param post 게시물 정보
     * @return 댓글 리스트
     */
    List<Comment> findByPost(Post post);
}
