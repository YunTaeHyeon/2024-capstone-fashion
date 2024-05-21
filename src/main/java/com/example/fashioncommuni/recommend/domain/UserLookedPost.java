package com.example.fashioncommuni.recommend.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserLookedPost {
    private Long userId;
    private List<Long> postId;

    @Builder
    public UserLookedPost(Long userId, List<Long> postId) {
        this.userId = userId;
        this.postId = postId;
    }

    public void addPostId(Long postId) {
        this.postId.add(postId);
    }

}
