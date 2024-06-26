package com.example.fashioncommuni.chat.domain;

import com.example.fashioncommuni.member.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;

@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ChatMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_map_id")
    private Long id;                                  // pk

    @ToString.Exclude
    @JoinColumn(name = "chat_map_user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;                              // 유저

    @ToString.Exclude
    @JoinColumn(name = "chat_map_chatroom_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private ChatRoom chatRoom;                        // 채팅방

    // private 생성자 선언
    private ChatMap(User user, ChatRoom chatRoom) {
        this.user = user;
        this.chatRoom = chatRoom;
    }

    // 생성자 factory method of
    public static ChatMap of(User user, ChatRoom chatRoom) {
        return new ChatMap(user, chatRoom);
    }

    // equals & hashCode 최적화
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMap chatMap)) return false;
        return this.id != null && Objects.equals(getId(), chatMap.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}