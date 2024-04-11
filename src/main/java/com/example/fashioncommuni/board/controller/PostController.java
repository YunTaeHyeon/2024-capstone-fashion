package com.example.fashioncommuni.board.controller;

import com.example.fashioncommuni.board.DTO.comment.CommentResponseDTO;
import com.example.fashioncommuni.board.DTO.image.PostImageUploadDTO;
import com.example.fashioncommuni.board.DTO.post.PostResponseDTO;
import com.example.fashioncommuni.board.DTO.post.PostWriteRequestDTO;
import com.example.fashioncommuni.board.service.CommentService;
import com.example.fashioncommuni.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import java.util.List;

@Controller // ToDo: RestController로 변경
@RequiredArgsConstructor
@RequestMapping("/post") //toDO: URL 통합
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;
    private final CommentService commentService;

    /**
     * 게시글 작성
     * @return 게시글 작성 페이지
     */
    @GetMapping("/write")
    public String writeForm() {
        return "post/write";
    }

    /**
     * 게시글 작성 post
     * @param postWriteRequestDTO 게시글 정보
     * @param authentication 유저 정보
     * @return 게시글 디테일 페이지
     */
    @PostMapping("/write")
    public String write(PostWriteRequestDTO postWriteRequestDTO,
                        @ModelAttribute PostImageUploadDTO postImageUploadDTO,
                        Authentication authentication) {

        logger.info("postImageDTO is {}", postImageUploadDTO);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        postService.savePost(postWriteRequestDTO, postImageUploadDTO, userDetails.getUsername());

        return "redirect:/";
    }

    /**
     * 게시글 상세 조회
     * @param post_id 게시글 ID
     * @param model
     * @return
     */
    @GetMapping("/{post_id}")
    public String postDetail(@PathVariable Long post_id, Model model) {
        PostResponseDTO result = postService.postDetail(post_id);
        List<CommentResponseDTO> commentResponseDTO = commentService.commentList(post_id);

        model.addAttribute("comments", commentResponseDTO);
        model.addAttribute("dto", result);
        model.addAttribute("id", post_id);

        return "post/detail";
    }

    /**
     * 게시글 수정
     * @param post_id 게시글 ID
     * @param model
     * @param authentication 유저 정보
     * @return 게시글 수정 페이지
     */
    @GetMapping("/{post_id}/update")
    public String postUpdateForm(@PathVariable Long post_id, Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PostResponseDTO result = postService.postDetail(post_id);
        // 추가 조건문

        model.addAttribute("dto", result);
        model.addAttribute("id", post_id);

        return "post/update";
    }

    /**
     * 게시글 수정 post
     * @param post_id 게시글 ID
     * @param postWriteRequestDTO 수정 정보
     * @return 게시글 상세 조회 페이지
     */
    @PostMapping("/{post_id}/update")
    public String postUpdate(@PathVariable Long post_id, PostWriteRequestDTO postWriteRequestDTO) {
        postService.postUpdate(post_id, postWriteRequestDTO);

        return "redirect:/post/" + post_id;
    }

    /**
     * 게시글 삭제
     * @param post_id 게시글 ID
     * @param authentication 유저 정보
     * @return
     */
    @GetMapping("/{post_id}/remove")
    public String postRemove(@PathVariable Long post_id, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        PostResponseDTO result = postService.postDetail(post_id);
        // 추가 조건문

        postService.postRemove(post_id);

        return "redirect:/";
    }
}
//
//    public PostController(PostService postService) {
//        this.postService = postService;
//    }
//
//
//
//    @PostMapping // POST /posts
//    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post, BindingResult bindingResult) {
//        // 게시물 유효성 검사
//        if (bindingResult.hasErrors()) {
//            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
//        }
//        // 게시물 생성
//        Post created = postService.createPost(post);
//        return new ResponseEntity<>(created, HttpStatus.CREATED);
//    }
//
//    @GetMapping("/{postId}") // GET /posts/{postId}
//    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
//        Post post = postService.getPost(postId);
//        return new ResponseEntity<>(post, HttpStatus.OK);
//    }
//
//    @PutMapping("/{postId}") // PUT /posts/{postId} //toDo: 위험한지 확인해봐야 함
//    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
//        // 게시물 수정
//        Post updated = postService.updatePost(postId, post);
//        return new ResponseEntity<>(updated, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{postId}") // DELETE /posts/{postId}
//    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
//        postService.deletePost(postId);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @GetMapping // GET /posts //toDo: 하지만 우리 메인페이지는 ai를 활용해야 함
//    public ResponseEntity<List<Post>> getPosts() {
//        List<Post> posts = postService.getPosts();
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }
//
//    @GetMapping("/users/{userId}") // GET /posts/users/{userId}
//    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
//        List<Post> posts = postService.getPostsByUserId(userId);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }
//
//    @GetMapping("/categories/{categoryId}") // GET /posts/categories/{categoryId}
//    public ResponseEntity<List<Post>> getPostsByCategoryId(@PathVariable Long categoryId) {
//        List<Post> posts = postService.getPostsByCategoryId(categoryId);
//        return new ResponseEntity<>(posts, HttpStatus.OK);
//    }
//
//    // 예외처리는 나중에 하겠음.
//}
