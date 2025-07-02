package com.example.fashioncommuni.chat.service.kafka;
;

import com.example.fashioncommuni.chat.domain.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageReceiver {
    private final SimpMessageSendingOperations template;

    @KafkaListener(topics = "chatting", containerFactory = "kafkaChatContainerFactory")
    public void receiveChatMessage(Message message) {
        template.convertAndSend("/sub/chatRoom/enter"+message.getChatRoomId(),message);
    }


}
