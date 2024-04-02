package com.example.fashioncommuni.board.controller;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")//toDO: URL 통합
public class PostController {
    private final com.example.fashioncommuni.board.service.PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping // POST /posts
    public ResponseEntity<Post> createPost(@Valid @RequestBody Post post, BindingResult bindingResult) {
        // 게시물 유효성 검사
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        // 게시물 생성
        Post created = postService.createPost(post);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}") // GET /posts/{postId}
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{postId}") // PUT /posts/{postId} //toDo: 위험한지 확인해봐야 함
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        // 게시물 수정
        Post updated = postService.updatePost(postId, post);
        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}") // DELETE /posts/{postId}
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping // GET /posts //toDo: 하지만 우리 메인페이지는 ai를 활용해야 함
    public ResponseEntity<List<Post>> getPosts() {
        List<Post> posts = postService.getPosts();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/users/{userId}") // GET /posts/users/{userId}
    public ResponseEntity<List<Post>> getPostsByUserId(@PathVariable Long userId) {
        List<Post> posts = postService.getPostsByUserId(userId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    @GetMapping("/categories/{categoryId}") // GET /posts/categories/{categoryId}
    public ResponseEntity<List<Post>> getPostsByCategoryId(@PathVariable Long categoryId) {
        List<Post> posts = postService.getPostsByCategoryId(categoryId);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // 예외처리는 나중에 하겠음.
}
