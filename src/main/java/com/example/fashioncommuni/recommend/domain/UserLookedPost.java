package com.example.fashioncommuni.recommend.domain;

import com.example.fashioncommuni.board.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLookedPost {
    @Id
    @Column(name = "UserLookedPost_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public UserLookedPost(Long userId, Post post) {
        this.userId = userId;
        this.post = post;
    }

}
