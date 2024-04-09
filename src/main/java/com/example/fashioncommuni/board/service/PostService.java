package com.example.fashioncommuni.board.service;

import com.example.fashioncommuni.board.DTO.image.PostImageUploadDTO;
import com.example.fashioncommuni.board.DTO.posts.PostResponseDTO;
import com.example.fashioncommuni.board.DTO.posts.PostWriteRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    /**
     * 게시글 작성
     * @param postsWriteRequestDTO 게시글 정보
     * @return 게시글 ID
     */
    Long savePost(PostWriteRequestDTO postsWriteRequestDTO,
                   PostImageUploadDTO postsImageUploadDTO,
                   String email);

    /**
     * 게시글 상세조회
     * @param post_id 게시글 ID
     * @return 게시글 정보
     */
    PostResponseDTO postsDetail(Long post_id);

    /**
     * 게시글 전체조회
     * @param pageable 페이징 처리
     * @return 게시글 목록 (페이징)
     */
    Page<PostResponseDTO> postsList(Pageable pageable);

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
     * @param postsWriteRequestDTO 수정 정보
     * @return 게시글 ID
     */
    Long postsUpdate(Long post_id, PostWriteRequestDTO postsWriteRequestDTO);

    /**
     * 게시글 삭제
     * @param post_id 게시글 ID
     */
    void postsRemove(Long post_id);
}
//    private final PostRepository postRepository; // PostRepository 의존성 주입
//
//    public PostService(PostRepository postRepository) {
//        this.postRepository = postRepository;
//    }
//
//    public Post createPost(Post post) {
//        post.setCreatedAt(LocalDateTime.now()); // 현재 시간을 설정 toDo: set은 발작버튼입니다.
//        return postRepository.save(post); // 저장 후 리턴
//    }
//
//    public Post getPost(Long postId) {
//        return postRepository.findById(postId).orElse(null); // postId로 조회
//    }
//
//    // 게시물 삭제 기능 구현
//    public void deletePost(Long postId) {
//        // postId를 이용하여 해당 게시물을 찾습니다.
//        Post post = postRepository.findById(postId)
//                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId));
//        // 데이터베이스에서 게시물을 삭제합니다.
//        postRepository.deleteById(postId); // postId로 삭제
//    }
//
//    public List<Post> getPosts() {
//        return postRepository.findAll(); // 모든 게시글 조회
//    }
//
//    public List<Post> getPostsByUserId(Long userId) {
//        return postRepository.findByUserId(userId); // userId로 조회
//    }
//
//    public List<Post> getPostsByCategoryId(Long categoryId) {
//        return postRepository.findByCategoryId(categoryId); // categoryId로 조회
//    }
//
//    public Post updatePost(Long postId, Post updatedPost) { // 게시물 수정
//        // 기존 게시물 조회
//        Post existingPost = getPost(postId); // 게시물 조회
//        if (existingPost == null) { // 게시물이 없으면
//            throw new IllegalArgumentException("게시물을 찾을 수 없습니다: " + postId); // 예외 발생
//        }
//
//        // 수정된 정보로 게시물 업데이트
//        existingPost.setTitle(updatedPost.getTitle()); // 제목 업데이트
//        existingPost.setBody(updatedPost.getBody()); // 내용 업데이트
//        // 필요한 경우 다른 속성도 업데이트할 수 있음
//
//        // toDO: 현재 시간으로 수정 시간 업데이트 나중에
//
//        // 업데이트된 게시물 저장 후 반환
//        return postRepository.save(existingPost); // 저장 후 리턴
//    }
