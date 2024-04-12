package com.example.fashioncommuni.board.controller;

import com.example.fashioncommuni.board.DTO.comment.CommentRequestDTO;
import com.example.fashioncommuni.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     * @param post_id 게시물
     * @param commentRequestDTO 댓글 정보
     * @param authentication 유저 정보
     * @return 게시물 상세 페이지
     */
    @PostMapping("/post/{post_id}/comment")
    public String writeComment(@PathVariable Long post_id, CommentRequestDTO commentRequestDTO, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        commentService.writeComment(commentRequestDTO, post_id, userDetails.getUsername());

        return "redirect:/post/" + post_id;
    }

    @ResponseBody
    @PostMapping("/post/{post_id}/comment/{comment_id}/update")
    public String updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, CommentRequestDTO commentRequestDTO) {
        commentService.updateComment(commentRequestDTO, comment_id);
        return "/post/" + post_id;
    }

    /**
     * 댓글 삭제
     * @param post_id 게시물
     * @param comment_id 댓글 ID
     * @return 해당 게시물 리다이렉트
     */
    @GetMapping("/post/{post_id}/comment/{comment_id}/remove")
    public String deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id) {
        commentService.deleteComment(comment_id);
        return "redirect:/post/" + post_id;
    }
}