package com.example.fashioncommuni.board.DTO.post;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.domain.PostImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class PostResponseDTO {

    private Long post_id;
    private String title;
    private String body; // toDo: body 말고 content로 변경
    private LocalDateTime createdAt;
    private Long user_id;
    private Long category_id;
    private String status;
    private String email;
    private List<String> imageUrls;

    @Builder
    public PostResponseDTO(Post post) {
        this.post_id = post.getPost_id();
        this.title = post.getTitle();
        this.body = post.getBody();
        this.createdAt = post.getCreatedAt();
        this.user_id = post.getUser().getId();
        this.category_id = post.getCategory_id();
        this.status = post.getStatus();
        this.email = post.getUser().getEmail();
        //this.imageUrls = post.getImages().stream()
        //        .map(PostImage::getUrl)
        //        .toList();
    }
}
