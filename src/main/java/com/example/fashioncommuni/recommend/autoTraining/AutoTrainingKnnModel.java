package com.example.fashioncommuni.recommend.autoTraining;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AutoTrainingKnnModel{

    @Value("${autoTraining}")
    private String autoTraining;

    @Scheduled(fixedDelay = 1000 * 60 * 60 * 24 * 7)
    //모델 학습을 마치고 이후 7일마다 모델을 업데이트
    public void autoTrainingKnnModel() {
        log.info("Auto training model Start");

        //모델 학습 코드
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(autoTraining);
            //toDo: 서버에 올리고 경로 변환 필요 -> 완료
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            log.info("Auto training model Error");
        }

        log.info("Auto training model End");
    }

}