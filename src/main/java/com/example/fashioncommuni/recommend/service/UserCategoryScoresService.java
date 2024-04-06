package com.example.fashioncommuni.recommend.service;

import java.util.List;
import java.util.Map;

public interface UserCategoryScoresService {
    void writeCsvUserCategoryScores();
    List<Double> calculateUserCategoryScores(Long userId);
    // 게시물을 조회했을 때 카테고리 스코어를 스케쥴링으로 계산하는 메서드
}
