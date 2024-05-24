package com.example.fashioncommuni.chat.controller;

import com.example.fashioncommuni.chat.domain.ChatMessage;
import com.example.fashioncommuni.chat.domain.ChatRoom;
import com.example.fashioncommuni.chat.domain.Message;
import com.example.fashioncommuni.chat.service.ChatService;
import com.example.fashioncommuni.chat.service.kafka.MessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Transactional
public class ChatController {
    @Value("${spring.kafka.consumer.topic}")
    private String topic;

    private final ChatService chatService;
    private final MessageSender sender;

    @MessageMapping("/chat/sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage){
        chatService.addChatToRoom(chatMessage.getChatRoomId(),chatMessage.getSenderId(),chatMessage.getContent());
        Message message=Message.builder()
                .senderId(chatMessage.getSenderId())
                .content(chatMessage.getContent())
                .createdAt(chatMessage.getCreatedAt())
                .readCount(chatMessage.getReadCount())
                .chatType(chatMessage.getChatType())
                .imageName(chatMessage.getImageName())
                .imageUrl(chatMessage.getImageUrl())
                .build();
        sender.send(topic,message);

    }
    @GetMapping("/chat/room/list")
    @ResponseBody
    public List<ChatRoom> list() {
        return chatService.getAllChatRooms();
    }

    @GetMapping("/chat/message/list")
    @ResponseBody
    public List<ChatMessage> getChatMessagesByRoomId(@RequestParam("chatRoomId") String chatRoomId) {
        return chatService.getChatMessagesByRoomId(chatRoomId);
    }
    @PostMapping("/chat/rooms")
    @ResponseBody
    public ChatRoom createChatRoom(@RequestParam("chatRoomName") String chatRoomName) {
        return chatService.createChatRoom(chatRoomName);
    }

}
