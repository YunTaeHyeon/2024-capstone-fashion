package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


//스케쥴링에 관한 메소드를 오버라이드하여 설정할 수 있게 해주는 클래스
@Configuration
public class TaskSchedulerConfig implements SchedulingConfigurer {
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskSchedulerCount());
    }
    @Bean(destroyMethod = "shutdown") //@Bean(destoryMethod = "shutdown")은 스프링 컨테이너가 종료될 때 Executor를 종료시키기 위한 메소드
    public Executor taskSchedulerCount() {
        return Executors.newScheduledThreadPool(2);
        //스케쥴링을 위한 스레드 풀을 생성 Executors.newScheduledThreadPool(2)는 2개의 스레드를 가지는 스레드 풀을 생성
    }
}
