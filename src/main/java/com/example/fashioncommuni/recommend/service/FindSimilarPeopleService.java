package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.board.DTO.post.PostResponseDTO;
import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.domain.UserLookedPost;
import com.example.fashioncommuni.recommend.repository.UserCategoryScoresRepository;
import com.example.fashioncommuni.recommend.repository.UserLookedPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindSimilarPeopleService {
    private final UserCategoryScoresRepository userCategoryScoresRepository;
    private final UserLookedPostRepository userLookedPostRepository;
    private final UserCategoryScoresService userCategoryScoresService;

    private List<Long> getSimilarUserIds(Long userId) {
        List<Long> similarUserIds = new ArrayList<>();
        UserCategoryScores userCategoryScores = userCategoryScoresRepository.findByUserId(userId).orElseThrow(
                ()-> new IllegalArgumentException("User not found"));

        List<Double> userScore = userCategoryScoresService.calculateUserCategoryScores(userId);
        String input = userScore.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(" "));

        System.out.println("userScore : "+ input);
        System.out.println("bash -c cd recommendation-system && python3 recommend.py " + input);

        try {
            String safeInput = "\"" + input + "\"";  // 입력값을 쿼터로 감싸기
            ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "cd recommendation-system && python3 recommend.py " + safeInput);
            //ProcessBuilder processBuilder = new ProcessBuilder("bash", "-c", "cd recommendation-system && python3 recommend.py " + input);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                // Assuming the output is a list of lists, each representing a category
                String[] categoryValues = line.substring(2, line.length() - 2).split(" ");
                for (String value : categoryValues) {
                    similarUserIds.add(Long.parseLong(value.trim())+1); //파이썬에서 인덱스로 받아왔기 때문에 +1 해줘야 함
                }
            }

            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        log.info("similarUserIds : " + similarUserIds.toString());
        return similarUserIds;
    }

    public List<Long> getRecommendPostIds(Long userId) {
        List<Long> recommendPostIds = new ArrayList<>();
        List<Long> recommendUserIds = getSimilarUserIds(userId);

        for (Long recommendUserId : recommendUserIds){
            List<UserLookedPost> recommendUser = userLookedPostRepository.findAllByUserId(recommendUserId);
            List<UserLookedPost> userLookedPost = userLookedPostRepository.findAllByUserId(userId);

            log.info("recommendUser : " + recommendUser.toString());
            log.info("userLookedPost : " + userLookedPost.toString());

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
