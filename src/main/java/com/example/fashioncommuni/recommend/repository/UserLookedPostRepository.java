package com.example.fashioncommuni.recommend.repository;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.recommend.domain.UserLookedPost;
import org.apache.kafka.common.quota.ClientQuotaAlteration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLookedPostRepository extends JpaRepository<UserLookedPost, Long> {
    List<UserLookedPost> findAllByUserId(Long userId);
    UserLookedPost findByUserIdAndPost(Long userId, Post post);
}
