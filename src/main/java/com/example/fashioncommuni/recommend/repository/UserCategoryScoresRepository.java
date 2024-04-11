package com.example.fashioncommuni.recommend.repository;

import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserCategoryScoresRepository extends JpaRepository<UserCategoryScores, Long> {
    Optional<UserCategoryScores> findByUserIdAndCategoryId(Long userId, Long categoryId);
}