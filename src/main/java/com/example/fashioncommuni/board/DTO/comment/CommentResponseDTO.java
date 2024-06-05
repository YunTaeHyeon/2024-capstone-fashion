package com.example.fashioncommuni.board.DTO.comment;

import com.example.fashioncommuni.board.domain.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDTO {

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long commentId;
    private String content;
    private String username;
    private String email;

    @Builder
    public CommentResponseDTO(Comment comment) {
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.commentId = comment.getCommentId();
        this.content = comment.getContent();
        this.username = comment.getUser().getUsername();
        this.email = comment.getUser().getEmail();
    }
}