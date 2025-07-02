package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.DTO.image.PostImageUploadDTO;
import com.example.fashioncommuni.board.DTO.post.PostResponseDTO;
import com.example.fashioncommuni.board.DTO.post.PostWriteRequestDTO;
import com.example.fashioncommuni.board.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    /**
     * 게시글 작성
     * @param postWriteRequestDTO 게시글 정보
     * @param postImageUploadDTO 이미지 정보
     * @param email 작성자
     * @return 게시글 ID
     */
    Long savePost(PostWriteRequestDTO postWriteRequestDTO,
                   PostImageUploadDTO postImageUploadDTO,
                   String email);

    /**
     * 게시글 상세조회
     * @param postId 게시글 ID
     * @return 게시글 정보
     */
    PostResponseDTO postDetail(Long postId);

    /**
     * 게시글 전체조회
     * @param pageable 페이징 처리
     * @return 게시글 목록 (페이징)
     */
    Page<PostResponseDTO> postList(Pageable pageable);

    //위 기능에서 추천 기능을 추가한 메소드
    Page<PostResponseDTO> recommendPostList(Long userId,Pageable pageable);

    /**
     * 게시글 검색
     * @param keyword 검색어
     * @param pageable 페이징 처리
     * @return 검색 된 게시글 목록 (페이징)
     */
    Page<PostResponseDTO> searchingPostList(String keyword, Pageable pageable);

    /**
     * 게시글 수정
     * @param postId 게시글 ID
     * @param postWriteRequestDTO 수정 정보
     * @return 게시글 ID
     */
    Long postUpdate(Long postId, PostWriteRequestDTO postWriteRequestDTO);

    /**
     * 게시글 삭제
     * @param postId 게시글 ID
     */
    void postRemove(Long postId);

    /**
     * 카테고리별 게시글 조회
     * @param categoryId 카테고리 ID
     * @param pageable 페이징 처리
     * @return 카테고리별 게시글 목록
     */
    Page<PostResponseDTO> searchingPostCategory(Long categoryId, Pageable pageable);
}