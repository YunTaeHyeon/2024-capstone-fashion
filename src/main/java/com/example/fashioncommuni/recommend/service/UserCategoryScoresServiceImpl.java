package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.repository.UserCategoryScoresRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryScoresServiceImpl implements UserCategoryScoresService{
    private final UserCategoryScoresRepository userCategoryScoresRepository;

    public UserCategoryScoresServiceImpl(UserCategoryScoresRepository userCategoryScoresRepository) {
        this.userCategoryScoresRepository = userCategoryScoresRepository;
    }
    @Override //toDo: 카테고리 점수를 계산할 때 점수를 남겼을 때, 조회했을 때 등등 각각 다른 메서드로 구현해야 할 듯.
    //toDO: 카테고리가 추가될 경우 기존의 데이터베이스를 어떻게 처리할 것인지 고민해야 함
    public void calculateUserCategoryScores(Long userId, Long categoryId) {
        // 사용자의 카테고리 점수를 계산하는 메서드
        // 사용자의 카테고리 점수를 계산하는 로직을 구현
        UserCategoryScores userCategoryScores = userCategoryScoresRepository.findByUserIdAndCategoryId(userId, categoryId)
                .orElseThrow(()->new IllegalArgumentException("사용자나 카테고리 점수를 찾을 수 없습니다: " + userId + ", " + categoryId));

        List<Double> scores = userCategoryScores.getScores();

        boolean allZero = true;
        for(Double score : scores){
            if (score != 0.0){
                allZero = false;
                break;
            }
        }

        if(allZero) {
            //처음으로 카테고리 점수를 남기는 경우
            //카테고리 점수를 계산
            //카테고리 점수를 저장
        }
        else{
            //카테고리 점수를 업데이트
            //카테고리 점수를 계산
            //카테고리 점수를 저장
        }
    }
}