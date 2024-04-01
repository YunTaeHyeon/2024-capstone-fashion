package com.example.fashioncommuni.member.domain;

import jakarta.persistence.*;

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;            // 유저pk

    @Column(name = "login_id", nullable = false)
    private String loginId;     // 로그인 ID

    @Column(name = "password", nullable = false)
    private String password;    // 로그인 비밀번호

    @Column(name = "username", nullable = false)
    private String username;    // 유저실명

    @Column(name = "email", nullable = false)
    private String email;       // 이메일

    @Column(name="age",nullable = false)
    private Integer age;
    @Column(name="gender",nullable = false)
    private String gender;


    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private RoleType roleType;  // 계정 타입

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    public User(String loginId, String password, String username, String email, Integer age, String gender, RoleType roleType, UserStatus userStatus) {
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.roleType = roleType;
        this.userStatus = userStatus;
    }
}
