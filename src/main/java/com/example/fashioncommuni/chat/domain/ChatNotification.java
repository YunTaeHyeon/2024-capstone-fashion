package com.example.fashioncommuni.chat.domain;

import com.example.fashioncommuni.member.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_notification_id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User senderId;

    @Column(name = "recipient_id", nullable = false)
    private String recipientId;

    @Column(name = "chat_room_id", nullable = false)
    private String chatRoomId;

    @Column(name = "content", nullable = false)
    private String content;



    public ChatNotification(User senderId, User recipientId, String chatRoomId, String content) {
        this.senderId = senderId;
        this.recipientId = recipientId.getLoginId();
        this.chatRoomId = chatRoomId;
        this.content = content;
    }


}