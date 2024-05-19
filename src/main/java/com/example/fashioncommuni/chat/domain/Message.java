package com.example.fashioncommuni.chat.domain;

import com.example.fashioncommuni.member.domain.User;
import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private String chatRoomId;

    private String senderId;

    private String content;

    private String createdAt;
    private int readCount;

    private ChatType chatType; // 채팅 타입 필드 추가('TEXT', 'IMAGE')

    private String imageName; // 이미지 파일 이름
    private String imageUrl; // 이미지 URL

    @Builder
    private Message(String chatRoomId, User sender, String content, String createdAt, int readCount, ChatType chatType, String imageName, String imageUrl) {
        this.chatRoomId = chatRoomId;
        this.senderId = sender.getLoginId();
        this.content = content;
        this.createdAt = createdAt;
        this.readCount = readCount;
        this.chatType = chatType;
        this.imageName = imageName;
        this.imageUrl = imageUrl;
    }
}