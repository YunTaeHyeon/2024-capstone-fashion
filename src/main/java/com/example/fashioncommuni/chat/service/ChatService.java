package com.example.fashioncommuni.chat.service;

import com.example.fashioncommuni.chat.domain.ChatMessage;
import com.example.fashioncommuni.chat.domain.ChatRoom;
import com.example.fashioncommuni.chat.domain.Message;
import com.example.fashioncommuni.chat.repository.ChatMessageRepository;
import com.example.fashioncommuni.chat.repository.ChatRoomRepository;
import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 채팅을 채팅방에 추가하는 메서드
    public void addChatToRoom(String chatRoomId, String senderId, String content) {

        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setChatRoomId(chatRoomId);
        chatMessage.setSenderId(senderId);
        chatMessage.setContent(content);
        chatMessageRepository.save(chatMessage);
    }

    // 채팅방의 아이디를 기반으로 채팅 내용을 불러오는 메서드
    public List<ChatMessage> getChatMessagesByRoomId(String chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }
    public ChatRoom createChatRoom(String chatRoomName) {
        ChatRoom chatRoom = ChatRoom.builder()
                .chatRoomId(UUID.randomUUID().toString())
                .chatRoomName(chatRoomName)
                .build();
        return chatRoomRepository.save(chatRoom);
    }
    public List<ChatRoom> getAllChatRooms() {
        return chatRoomRepository.findAll();
    }


}
