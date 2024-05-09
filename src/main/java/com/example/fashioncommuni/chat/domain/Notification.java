package com.example.fashioncommuni.chat.domain;


import com.example.fashioncommuni.member.domain.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification implements Serializable {

    private Long chatRoomId;
    private Long recipientId;
    private String senderNick;
    private LocalDateTime createdDate;
    private String content;

    public static Notification createReadCountUpdateNotification(Long chatRoomId, User sender) {
        return new Notification(chatRoomId,
                sender.getId(), // 기존의 senderId 대신 User 엔티티를 전달
                sender.getUsername(), // 유저의 닉네임 사용
                LocalDateTime.now(),
                null);
    }
}