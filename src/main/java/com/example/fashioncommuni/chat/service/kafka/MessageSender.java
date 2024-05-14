package com.example.fashioncommuni.chat.service.kafka;

import com.example.fashioncommuni.chat.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSender {
    private final KafkaTemplate<String, Message> chatKafkaTemplate;

    // 메시지를 지정한 Kafka 토픽으로 전송
    public void send(String topic, Message data) {
        chatKafkaTemplate.send(topic, data);
        log.info("Sent message: {} to topic: {}", data, topic);
    }
}
