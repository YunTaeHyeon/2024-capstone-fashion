package com.example.board.controller;

import com.example.board.domain.Post;
import com.example.board.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping // POST /posts
    public ResponseEntity<Post> createPost(@RequestBody Post post) {
        Post createdPost = postService.createPost(post);
        return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}") // GET /posts/{postId}
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    @PutMapping("/{postId}") // PUT /posts/{postId}
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody Post post) {
        post.setPostId(postId);
        Post updatedPost = postService.updatePost(post);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}") // DELETE /posts/{postId}
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping // GET /posts
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

    @ExceptionHandler(IllegalArgumentException.class) // IllegalArgumentException 발생 시 처리
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class) // Exception 발생 시 처리
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(RuntimeException.class) // RuntimeException 발생 시 처리
    public ResponseEntity<String> handleRuntimeException(RuntimeException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Throwable.class) // Throwable 발생 시 처리
    public ResponseEntity<String> handleThrowable(Throwable e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Error.class) // Error 발생 시 처리
    public ResponseEntity<String> handleError(Error e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
