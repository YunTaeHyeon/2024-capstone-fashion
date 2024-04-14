package com.example.fashioncommuni.recommend.service;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.repository.PostRepository;
import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.repository.UserCategoryScoresRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserCategoryScoresServiceImpl implements UserCategoryScoresService {
    private final UserCategoryScoresRepository userCategoryScoresRepository;
    private final PostRepository postRepository;
    private static final int MAX_USE_SCORES = 50; //추천에 사용할 최대 점수의 개수 //toDo: 변경 가능

    public UserCategoryScoresServiceImpl(UserCategoryScoresRepository userCategoryScoresRepository, PostRepository postRepository){
        this.userCategoryScoresRepository = userCategoryScoresRepository;
        this.postRepository = postRepository;
    }

    //toDo: 카테고리 점수를 계산할 때 점수를 남겼을 때, 조회했을 때 등등 각각 다른 메서드로 구현해야 할 듯.
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24 * 7)
    @Override
    public void writeCsvUserCategoryScores() { //csv 파일로 사용자의 카테고리 점수를 저장하는 함수(KNN 모델의 학습 자료 생성)
        List<List<Double>> writeCsv = new ArrayList<>(); //writeCsv는 Double로 user_id와 각 카테고리의 가중치로 이루어진 리스트

        List<UserCategoryScores> userCategoryScoresList = userCategoryScoresRepository.findAll();
        //현재는 모든 사용자의 유저 카테고리 스코어를 가져오지만 만일 필요하다면 특정 사용자의 카테고리 스코어를 가져올 수도 있음
        for (UserCategoryScores userCategoryScores : userCategoryScoresList) {
            writeCsv.add(calculateUserCategoryScores(userCategoryScores.getUser().getId()));
        }

        // 현재 날짜를 포함한 파일 이름 생성
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter);
        String fileName = "user_category_scores_" + dateString + ".csv"; //파일 이름 변경 가능
        String filePath = fileName;
        //String filePath = "path/to/save/" + fileName; //파일 경로 변경 가능 toDo: 서버에 올리면 파일 경로 설정

        // CSV 파일에 데이터 쓰기
        try {
            FileWriter csvWriter = new FileWriter(filePath);
            for (List<Double> rowData : writeCsv) {
                csvWriter.append(String.join(",", rowData.stream().map(String::valueOf).collect(Collectors.toList())));
                csvWriter.append("\n");
            }
            csvWriter.flush();
            csvWriter.close();

            log.info(filePath + " 생성 완료");

        } catch (IOException e) {
            log.info(filePath + " 생성 실패");
            e.printStackTrace();
        }

    }


    @Override
    public List<Double> calculateUserCategoryScores(Long userId) {  // 사용자의 카테고리 점수를 계산하는 함수
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

        // userId와 각 카테고리의 가중치를 계산하여 리스트에 추가합니다. userId는 0번째 인덱스에 저장됩니다.
        List<Double> finalScores = new ArrayList<>();
        finalScores.add(userId.doubleValue());

        for (double score : categorySumMap.values()) {
            finalScores.add(score / totalScore);
        }
        return finalScores;
    }

    @Override
    @Transactional
    public CategoryScores viewPost(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다.: " + postId));

        // 게시물의 카테고리를 가져옵니다.
        Long categoryId = post.getCategory_id();

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

        // 사용자의 카테고리 점수를 저장합니다.
        userCategoryScoresRepository.save(userCategoryScores);

        return categoryScores;

    }

    @Override
    public UserCategoryScores findUserCategoryScoresByUserId(Long userId){
        return userCategoryScoresRepository.findByUserId(userId).get();
    }
}