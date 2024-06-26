package com.example.fashioncommuni.chat.repository;

import com.example.fashioncommuni.chat.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT m FROM Message m WHERE m.chatRoom.id = :chatroomId")
    List<Message> findByChatRoomId(@Param("chatroomId") Long chatroomId);

}