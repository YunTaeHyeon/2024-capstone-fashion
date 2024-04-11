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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String title;

    @NotEmpty(message = "내용은 필수 입력값입니다.")
    @Column(columnDefinition = "TEXT")
    private String body; // toDo: ERD 타입명 변경하기

    // user_id 외래키 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "category_id")
    private Long category_id;

    private String status;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("comment_id asc")
    private List<Comment> comment;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @OrderBy("image_id asc")
    private List<PostImage> image;

    public void update(String title, String body) {
        this.title = title;
        this.body = body;
    }
}