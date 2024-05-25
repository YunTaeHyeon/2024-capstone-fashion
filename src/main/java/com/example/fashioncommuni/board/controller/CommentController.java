package com.example.fashioncommuni.board.controller;

import com.example.fashioncommuni.board.DTO.comment.CommentRequestDTO;
import com.example.fashioncommuni.board.service.CommentService;
import com.example.fashioncommuni.member.dto.SecurityUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     * @param postId 게시물
     * @param commentRequestDTO 댓글 정보
     * @param authentication 유저 정보
     * @return 게시물 상세 페이지
     */
    @PostMapping("/post/{postId}/comment")
    public String writeComment(@PathVariable Long postId, CommentRequestDTO commentRequestDTO, Authentication authentication) {
        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        commentService.writeComment(commentRequestDTO, postId, userDetails.getEmail());

        return "redirect:/post/" + postId;
    }

    /**
     * 댓글 수정
     * @param postId 게시물
     * @param comment_id 댓글 ID
     * @param commentRequestDTO 댓글 정보
     * @return 해당 게시물 리다이렉트
     */
    @ResponseBody
    @PostMapping("/post/{postId}/comment/{comment_id}/update")
    public String updateComment(@PathVariable Long postId, @PathVariable Long comment_id, CommentRequestDTO commentRequestDTO) {
        commentService.updateComment(commentRequestDTO, comment_id);
        return "redirect:/post/" + postId;
    }

    /**
     * 댓글 삭제
     * @param postId 게시물
     * @param comment_id 댓글 ID
     * @return 해당 게시물 리다이렉트
     */
    @GetMapping("/post/{postId}/comment/{comment_id}/remove")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long comment_id) {
        commentService.deleteComment(comment_id);
        return "redirect:/post/" + postId;
    }
}