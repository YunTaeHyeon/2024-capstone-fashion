package com.example.fashioncommuni.chat.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "chat_message")
public class ChatMessage {

    @Id
    private String id;
    @Indexed
    private Long chatRoomId;
    private Long senderId;
    private String content;

    private String createdAt;

    private ChatType chatType; // 채팅 타입 필드 추가('TEXT', 'IMAGE')
    private String imageUrl;
}