package com.example.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AutoTrainingKnnModel{
    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24 * 7) //모델 학습을 마치고 이후 7일마다 모델을 업데이트
    public void autoTrainingKnnModel() throws Exception{
        System.out.println("KNN 모델 학습 시작");

        //모델 학습 시작
        log.info("KNN 모델 학습 시작");

        Runtime runtime = Runtime.getRuntime();

        try {
            Process process = runtime.exec("python3 /Users/taehyeonyun/coding/2024-capstone-fashion/recommendation-system/train_category_recommendation_model.py");
            //python3 /Users/taehyeonyun/coding/2024-capstone-fashion/recommendation-system/train_category_recommendation_model.py 명령어를 실행 -> 경로는 추후 서버에 맞게 설정
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //모델 학습 완료
        log.info("KNN 모델 학습 완료");
    }

}
