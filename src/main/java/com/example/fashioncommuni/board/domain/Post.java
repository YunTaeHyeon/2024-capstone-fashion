package com.example.fashioncommuni.board.domain;

import com.example.fashioncommuni.member.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @Column(name = "postId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 입력값입니다.")
    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "category_id")
    private Long categoryId;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("comment_id asc")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("image_id asc")
    private List<PostImage> postImages;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}