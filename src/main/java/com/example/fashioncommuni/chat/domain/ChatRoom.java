package com.example.fashioncommuni.chat.domain;

import com.example.fashioncommuni.member.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long chatRoomId;                            // pk

    @Column(name = "chatroom_name")
    private  String chatRoomName;                // 채팅방 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    public  ChatRoom createNewChatRoom(User sender, User recipient) {
        return ChatRoom.builder()
                .chatRoomName(chatRoomName)
                .sender(sender)
                .recipient(recipient)
                .build();
    }

}