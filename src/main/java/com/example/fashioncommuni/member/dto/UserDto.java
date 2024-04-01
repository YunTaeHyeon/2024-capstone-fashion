package com.example.fashioncommuni.member.dto;

import com.example.fashioncommuni.member.domain.RoleType;
import com.example.fashioncommuni.member.domain.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

public record UserDto(
        Long userId,
        String loginId,
        String username,
        String password,
        UserStatus status,
        String email,
        Integer age,
        String gender,
        RoleType roleType,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
    // factory method of 선언
    public static UserDto of(Long userId, String loginId, String username, String password, UserStatus status, String email, int age, String gender,RoleType roleType, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new UserDto(userId, loginId, username, password, status, email, age,gender,roleType, createdAt, updatedAt);
    }
    public static UserDto of (String loginId){
        return new UserDto(null,loginId,null,null,null,null,null,null,null,null,null
        );
    }
}
