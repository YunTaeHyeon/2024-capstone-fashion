package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.domain.UserLookedPost;
import com.example.fashioncommuni.recommend.repository.UserCategoryScoresRepository;
import com.example.fashioncommuni.recommend.repository.UserLookedPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FindSimilarPeopleService {
    private final UserCategoryScoresRepository userCategoryScoresRepository;
    private final UserLookedPostRepository userLookedPostRepository;

    private List<Long> getSimilarUserIds(Long userId) {
        List<Long> similarUserIds = new ArrayList<>();
        UserCategoryScores userCategoryScores = userCategoryScoresRepository.findByUserId(userId).orElseThrow(
                ()-> new IllegalArgumentException("User not found"));

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "cd recommendation-system && python3 recommend.py " + userCategoryScores.getScores().toString());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the output is a list of lists, each representing a category
                String[] categoryValues = line.substring(2, line.length() - 2).split(" ");
                for (String value : categoryValues) {
                    similarUserIds.add(Long.parseLong(value.trim()));
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return similarUserIds;
    }

    public List<Long> getRecommendPostIds(Long userId) {
        List<Long> recommendPostIds = new ArrayList<>();
        List<Long> recommendUserIds = getSimilarUserIds(userId);

        for (Long recommendUserId : recommendUserIds){
            List<UserLookedPost> recommendUser = userLookedPostRepository.findAllByUserId(recommendUserId);
            List<UserLookedPost> userLookedPost = userLookedPostRepository.findAllByUserId(userId);

            //recommendUser의 Post와 userLookedPost의 Post를 비교하여 recommendPostIds에 추가
            for (UserLookedPost recommendPost : recommendUser){
                if (!userLookedPost.contains(recommendPost)){
                    recommendPostIds.add(recommendPost.getPost().getPostId());
                }
            }
        }
        return recommendPostIds;
    }
}
