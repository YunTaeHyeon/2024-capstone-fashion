package com.example.fashioncommuni.chat.repository;

import com.example.fashioncommuni.chat.domain.ChatMap;
import com.example.fashioncommuni.chat.domain.ChatRoom;
import com.example.fashioncommuni.member.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ChatMapRepository extends JpaRepository<ChatMap, Long> { ;

    Optional<ChatMap> findByUserAndChatRoom(User user, ChatRoom chatRoom);

}