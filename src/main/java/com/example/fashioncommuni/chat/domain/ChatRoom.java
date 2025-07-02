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
    @Column(name = "room_id")
    private Long id;// pk

    @Column(name = "chatroom_id")
    private String chatRoomId;

    @Column(name = "chatroom_name")
    private  String chatRoomName;                // 채팅방 이름





}