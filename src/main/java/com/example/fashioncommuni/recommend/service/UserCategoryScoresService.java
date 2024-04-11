package com.example.fashioncommuni.recommend.service;

import java.util.List;

public interface UserCategoryScoresService {
    void calculateUserCategoryScores(Long userId, Long categoryId);
    // 게시물을 조회했을 때 카테고리 스코어를 계산하는 메서드
}