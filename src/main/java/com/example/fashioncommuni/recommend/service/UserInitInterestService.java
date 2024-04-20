package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.repository.UserRepository;
import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.repository.UserCategoryScoresRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserInitInterestService {
    private final UserCategoryScoresRepository userCategoryScoresRepository;
    private final UserRepository userRepository;

    public UserInitInterestService(UserCategoryScoresRepository userCategoryScoresRepository, UserRepository userRepository){
        this.userCategoryScoresRepository = userCategoryScoresRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CategoryScores selectImageForInitInterest(Long userId, String categoryId) {
        // 트랜잭션 내에서 엔티티를 조회하고 변경하도록 합니다.
        UserCategoryScores userCategoryScores = userCategoryScoresRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IllegalArgumentException("User not found"));
                    // 새로운 사용자 카테고리 점수 생성
                    UserCategoryScores newUserCategoryScores = UserCategoryScores.builder()
                            .user(user)
                            .scores(new LinkedHashMap<>())
                            .build();
                    return userCategoryScoresRepository.save(newUserCategoryScores); // 저장 후 반환
                });

        Map<String, CategoryScores> scoresMap = userCategoryScores.getScores();

        CategoryScores categoryScores = scoresMap.get(categoryId);

        if (categoryScores == null) {
            List<Double> initialScores = new ArrayList<>();
            initialScores.add(1.0);
            // 초기 점수로 1.0을 포함하는 ArrayList를 생성합니다.
            categoryScores = CategoryScores.builder()
                    .userCategoryScores(userCategoryScores)
                    .category(categoryId)
                    .scores(initialScores)
                    .build();
            scoresMap.put(categoryId, categoryScores);
            log.info("null이어서 생성",categoryScores.toString());

        } else {
            // 카테고리 점수를 업데이트합니다.
            categoryScores.addCategoryScores(1.0);
        }

        // 변경된 엔티티를 저장합니다.
        //userCategoryScoresRepository.save(userCategoryScores);

        return categoryScores;
    }
}