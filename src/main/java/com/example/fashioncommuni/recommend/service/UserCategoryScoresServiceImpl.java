package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.repository.UserCategoryScoresRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserCategoryScoresServiceImpl implements UserCategoryScoresService {
    private final UserCategoryScoresRepository userCategoryScoresRepository;
    private static final int MAX_USE_SCORES = 50; //추천에 사용할 최대 점수의 개수

    public UserCategoryScoresServiceImpl(UserCategoryScoresRepository userCategoryScoresRepository) {
        this.userCategoryScoresRepository = userCategoryScoresRepository;
    }

    //toDo: 카테고리 점수를 계산할 때 점수를 남겼을 때, 조회했을 때 등등 각각 다른 메서드로 구현해야 할 듯.

    //@Scheduled(fixedDelay = 1000 * 60 * 60 * 24 * 7) 다른 곳에서 적용 필요
    @Override
    public List<Double> calculateUserCategoryScores(Long userId) {
        // 사용자의 카테고리 점수를 스케쥴링으로 계산하는 함수
        UserCategoryScores userCategoryScores = userCategoryScoresRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.: " + userId));

        // 최근 MAX_USE_SCORES 개의 카테고리 점수만 유지하기 위해 LinkedHashmap을 사용합니다.
        Map<Long, CategoryScores> scoresMap = userCategoryScores.getScores();

        // LinkedHashmap의 크기가 50을 초과하면, 가장 오래된 데이터를 삭제합니다.
        if (scoresMap.size() > MAX_USE_SCORES) {
            List<Long> keysToRemove = new ArrayList<>(scoresMap.keySet()); //scoresMap에 저장된 키의 순서대로 가져옴
            for (int i = 0; i < scoresMap.size() - MAX_USE_SCORES; i++) { //MAX_USE_SCORES 넘었을 경우에 그 다음부터 삭제
                scoresMap.remove(keysToRemove.get(i));
            }
        }
        // 각 카테고리의 점수를 합산하여 카테고리별 총 점수 맵을 생성합니다.
        Map<Long, Double> categorySumMap = scoresMap.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().getScores().stream().mapToDouble(Double::doubleValue).sum()
                ));

        // 전체 점수 합계를 계산합니다.
        double totalScore = categorySumMap.values().stream().mapToDouble(Double::doubleValue).sum();

        // 각 카테고리의 가중치를 계산하여 리스트에 추가합니다.
        List<Double> finalScores = new ArrayList<>();
        for (double score : categorySumMap.values()) {
            finalScores.add(score / totalScore);
        }
        return finalScores;
    }
}
