package com.example.fashioncommuni.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AutoTrainingKnnModel{
    @Scheduled(fixedDelay = 1000) //모델 학습을 마치고 이후 7일마다 모델을 업데이트
    public void autoTrainingKnnModel() {
        System.out.println("KNN 모델 학습 시작");

    }

}
