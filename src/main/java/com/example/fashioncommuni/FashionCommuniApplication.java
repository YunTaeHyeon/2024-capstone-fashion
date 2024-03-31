package com.example.fashioncommuni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //스케쥴링을 사용할 수 있게 선언
@EnableAsync //비동기적 실행을 선언
public class FashionCommuniApplication {

	public static void main(String[] args) {
		SpringApplication.run(FashionCommuniApplication.class, args);
	}

}
