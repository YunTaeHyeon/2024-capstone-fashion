package com.example.fashioncommuni.member.dto;

import com.example.fashioncommuni.member.domain.RoleType;
import com.example.fashioncommuni.member.domain.User;
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
        RoleType roleType

) {
    // factory method of 선언
    public static UserDto of(Long userId, String loginId, String username, String password, UserStatus status, String email, int age, String gender,RoleType roleType) {
        return new UserDto(userId, loginId, username, password, status, email, age,gender,roleType);
    }
    public static UserDto of (String loginId){
        return new UserDto(null,loginId,null,null,null,null,null,null,null
        );
    }
    // Principal에서 사용할 factory method of 선언
    public static UserDto of(Long userId, String loginId, String username, String password, String email, RoleType roleType) {
        return new UserDto(
                userId, loginId, username, password, null, email, null, null,roleType
        );
    }
    public static UserDto fromEntity(User entity) {
        return UserDto.of(
                    entity.getId(),
                    entity.getLoginId(),
                    entity.getUsername(),
                    entity.getPassword(),
                    entity.getUserStatus(),
                    entity.getEmail(),
                    entity.getAge(),
                    entity.getGender(),
                    entity.getRoleType()
            );
        }
}
