package com.example.fashioncommuni.chat.service;

import com.example.fashioncommuni.chat.domain.ChatMessage;
import com.example.fashioncommuni.chat.domain.ChatRoom;
import com.example.fashioncommuni.chat.repository.ChatMessageRepository;
import com.example.fashioncommuni.member.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;

    @Autowired
    public ChatService(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    // 채팅을 채팅방에 추가하는 메서드
    public void addChatToRoom(ChatRoom chatRoom, User sender, String content) {
        ChatMessage chatMessage = ChatMessage.builder()
                .chatRoomId(chatRoom.getChatRoomId())
                .senderId(sender.getId())
                .content(content)
                .build();
        chatMessageRepository.save(chatMessage);
    }

    // 채팅방의 아이디를 기반으로 채팅 내용을 불러오는 메서드
    public List<ChatMessage> getChatMessagesByRoomId(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }
}
