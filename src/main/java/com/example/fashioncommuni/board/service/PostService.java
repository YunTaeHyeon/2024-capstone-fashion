package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.DTO.image.PostImageUploadDTO;
import com.example.fashioncommuni.board.DTO.post.PostResponseDTO;
import com.example.fashioncommuni.board.DTO.post.PostWriteRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    /**
     * 게시글 작성
     * @param postWriteRequestDTO 게시글 정보
     * @param email 작성자
     * @return 게시글 ID
     */
    Long savePost(PostWriteRequestDTO postWriteRequestDTO,
                   PostImageUploadDTO postImageUploadDTO,
                   String email);

    /**
     * 게시글 상세조회
     * @param post_id 게시글 ID
     * @return 게시글 정보
     */
    PostResponseDTO postDetail(Long post_id);

    /**
     * 게시글 전체조회
     * @param pageable 페이징 처리
     * @return 게시글 목록 (페이징)
     */
    Page<PostResponseDTO> postList(Pageable pageable);

    /**
     * 게시글 검색
     * @param keyword 검색어
     * @param pageable 페이징 처리
     * @return 검색 된 게시글 목록 (페이징)
     */
    Page<PostResponseDTO> searchingPostList(String keyword, Pageable pageable);

    /**
     * 게시글 수정
     * @param post_id 게시글 ID
     * @param postWriteRequestDTO 수정 정보
     * @return 게시글 ID
     */
    Long postUpdate(Long post_id, PostWriteRequestDTO postWriteRequestDTO);

    /**
     * 게시글 삭제
     * @param post_id 게시글 ID
     */
    void postRemove(Long post_id);
}