package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.member.repository.UserRepository;
import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.repository.UserCategoryScoresRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class UserInitInterestService {
    // 사용자의 초기 관심사를 설정하는 서비스
    private final UserCategoryScoresRepository userCategoryScoresRepository;
    private final UserRepository userRepository;

    public UserInitInterestService(UserCategoryScoresRepository userCategoryScoresRepository, UserRepository userRepository){
        this.userCategoryScoresRepository = userCategoryScoresRepository;
        this.userRepository = userRepository;
    }

    public CategoryScores selectImageForInitInterest(Long userId, Long categoryId){ //UserCategoryScoresServiceImpl의 ViewPost() 함수와 똑같으나 차이점은 ViewPost는 postId를 인자로 받음
        // 사용자의 카테고리 점수를 가져옵니다.
        UserCategoryScores userCategoryScores = userCategoryScoresRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다.: " + userId));

        // 사용자의 카테고리 점수 맵을 가져옵니다.
        Map<Long, CategoryScores> scoresMap = userCategoryScores.getScores();

        // userCategoryScores에서 categoryId와 대치되는 CategoryScores를 가져옵니다.
        CategoryScores categoryScores = scoresMap.get(categoryId);

        // 카테고리 점수가 없을 경우, 새로운 카테고리 점수를 생성합니다.
        if (categoryScores == null) {
            categoryScores = CategoryScores.builder()
                    .userCategoryScores(userCategoryScores)
                    .category(categoryId.toString()) //카테고리 id를 문자열로 변환하여 저장
                    .scores(new ArrayList<>())
                    .build();
            scoresMap.put(categoryId, categoryScores);
        }

        // 카테고리 점수를 업데이트합니다.
        categoryScores.addCategoryScores(1.0);

        userCategoryScoresRepository.save(userCategoryScores);

        return categoryScores;
    }

}
