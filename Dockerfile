FROM openjdk:17-slim

# Debian에서는 apt를 사용하여 패키지를 설치합니다.
RUN apt-get update && apt-get install -y --no-install-recommends \
    bash \
    && rm -rf /var/lib/apt/lists/*

# JAR 파일을 복사합니다.
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# wait-for-it.sh 스크립트를 복사하고 실행 권한을 부여합니다.
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
