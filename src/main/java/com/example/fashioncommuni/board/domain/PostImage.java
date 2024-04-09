package com.example.fashioncommuni.board.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts_image") // toDo: ERD 타입명 변경하기
public class PostImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long image_id;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
