package com.example.fashioncommuni.chat.repository;

import com.example.fashioncommuni.chat.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findByChatRoomName(String chatRoomName);
}