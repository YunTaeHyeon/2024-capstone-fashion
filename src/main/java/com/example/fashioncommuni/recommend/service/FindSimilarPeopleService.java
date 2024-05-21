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
            ProcessBuilder processBuilder = new ProcessBuilder("python3", "recommend.py", userCategoryScores.getScores().toString());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming the output is comma separated IDs
                String[] ids = line.split(",");
                for (String id : ids) {
                    similarUserIds.add(Long.parseLong(id.trim()));
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
            UserLookedPost recommendUser = userLookedPostRepository.findByUserId(recommendUserId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            //recommendUser이 조회한 게시물에서 userId가 조회하지 않은 게시물을 추천
            for (Long postId : recommendUser.getPostId()){
                Optional<UserLookedPost> userLookedPost = userLookedPostRepository.findByUserId(userId);
                if (userLookedPost.isPresent() && !userLookedPost.get().getPostId().contains(postId)){
                    recommendPostIds.add(postId);
                }
            }

        }
        return recommendPostIds;
    }
}
