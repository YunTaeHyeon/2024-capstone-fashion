package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.recommend.domain.CategoryScores;

import java.util.List;
import java.util.Map;

public interface UserCategoryScoresService {
    void writeCsvUserCategoryScores();
    // 사용자의 카테고리 점수를 csv 파일로 저장하는 함수
    List<Double> calculateUserCategoryScores(Long userId);
    // 게시물을 조회했을 때 카테고리 스코어를 계산하는 함수
    CategoryScores viewPost(Long userId, Long postId);
    // 게시물을 조회했을 때 카테고리 스코어를 계산하는 함수

    // toDO: ratingPost() 함수 구현
    // CategoryScores ratingPost()
    // 게시물을 평가했을 때 카테고리 스코어를 계산하는 함수

}
