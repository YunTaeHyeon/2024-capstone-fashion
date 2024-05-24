package com.example.fashioncommuni.recommend.repository;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.recommend.domain.UserLookedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLookedPostRepository extends JpaRepository<UserLookedPost, Long> {
    Optional<UserLookedPost> findByUserId(Long userId);
    Optional<UserLookedPost> findByPost(Post post);
    List<UserLookedPost> findAllByUserId(Long userId);
}
