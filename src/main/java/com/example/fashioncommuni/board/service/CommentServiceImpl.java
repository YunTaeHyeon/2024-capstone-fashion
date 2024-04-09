package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.DTO.comment.CommentRequestDTO;
import com.example.fashioncommuni.board.DTO.comment.CommentResponseDTO;
import com.example.fashioncommuni.board.domain.Comment;
import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.repository.UserRepository;
import com.example.fashioncommuni.board.repository.CommentRepository;
import com.example.fashioncommuni.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository; // 댓글 레포지토리
    private final PostRepository postsRepository; // 게시물 레포지토리

    @Override
    public Long writeComment(CommentRequestDTO commentRequestDTO, Long posts_id, String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new IllegalArgumentException("이메일이 존재하지 않습니다."));
        Post posts = postsRepository.findById(posts_id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        Comment result = Comment.builder()
                .content(commentRequestDTO.getContent())
                .post(posts)
                .user(user)
                .build();
        commentRepository.save(result);

        return result.getComment_id();
    }
    // 댓글 작성
    @Override
    public List<CommentResponseDTO> commentList(Long comment_id) {
        Post posts = postsRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        List<Comment> comments = commentRepository.findByPost(posts);

        return comments.stream()
                .map(comment -> CommentResponseDTO.builder()
                        .comment(comment)
                        .build())
                .collect(Collectors.toList());

    }

    @Override
    public void updateComment(CommentRequestDTO commentRequestDTO, Long comment_id) {
        Comment comment = commentRepository.findById(comment_id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));
        comment.update(commentRequestDTO.getContent());
        commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long comment_id) {
        commentRepository.deleteById(comment_id);
    }
}