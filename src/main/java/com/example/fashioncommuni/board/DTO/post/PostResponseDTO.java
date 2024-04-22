package com.example.fashioncommuni.board.DTO.post;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.domain.PostImage;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostResponseDTO {

    private Long post_id;
    private String title;
    private String content;
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
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.user_id = post.getUser().getId();
        this.category_id = post.getCategory_id();
        this.status = post.getStatus();
        this.email = post.getUser().getEmail();
        this.imageUrls = post.getPostImages().stream()
                .map(PostImage::getUrl)
                .collect(Collectors.toList());
    }
}
