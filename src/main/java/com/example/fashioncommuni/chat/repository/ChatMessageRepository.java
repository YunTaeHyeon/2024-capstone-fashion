package com.example.fashioncommuni.chat.repository;

import com.example.fashioncommuni.chat.domain.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatMessageRepository extends MongoRepository<ChatMessage,String> {
    // 채팅방의 아이디를 기반으로 채팅 내용을 불러오는 메서드
    List<ChatMessage> findByChatRoomId(String chatRoomId);

    // 채팅을 채팅방에 추가하는 메서드
    ChatMessage save(ChatMessage chatMessage);

}
