package com.example.fashioncommuni.chat.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Getter
@Document(collection = "chat_message")
public class ChatMessage {

    @Id
    private String id;
    @Indexed
    private String chatRoomId;
    private String senderId;
    private String content;

    private String createdAt;
    private int readCount;

    private ChatType chatType; // 채팅 타입 필드 추가('TEXT', 'IMAGE')
    private String imageName; // 이미지 파일 이름
    private String imageUrl;


}