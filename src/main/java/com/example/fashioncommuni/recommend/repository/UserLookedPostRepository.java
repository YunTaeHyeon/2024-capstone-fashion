package com.example.fashioncommuni.recommend.repository;

import com.example.fashioncommuni.recommend.domain.UserLookedPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLookedPostRepository extends JpaRepository<UserLookedPost, Long> {
    Optional<UserLookedPost> findByUserId(Long userId);
}
