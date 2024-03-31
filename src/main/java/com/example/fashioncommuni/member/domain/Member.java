package com.example.fashioncommuni.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member {
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String nickname;

    private int age;

    private String gender;
    @Builder
    public Member(String username, String password, String nickname, int age, String gender) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.age = age;
        this.gender = gender;
    }
}