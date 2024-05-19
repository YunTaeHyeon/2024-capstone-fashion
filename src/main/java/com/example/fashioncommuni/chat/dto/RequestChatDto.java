package com.example.fashioncommuni.chat.dto;

import com.example.fashioncommuni.chat.domain.ChatType;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class RequestChatDto {
    private final String chatRoomId;

    private final String senderId;

    private final String content;

    private final String createdAt;
    private final int readCount;

    private final ChatType chatType; // 채팅 타입 필드 추가('TEXT', 'IMAGE')

    private final String imageName; // 이미지 파일 이름
    private final String imageUrl; // 이미지 URL
}
