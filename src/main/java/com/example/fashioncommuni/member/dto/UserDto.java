package com.example.fashioncommuni.member.dto;

import com.example.fashioncommuni.member.domain.RoleType;
import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.domain.UserStatus;

public record UserDto(
        Long userId,
        String loginId,
        String password, // 비밀번호의 위치를 조정
        String username,
        String email,
        Integer age,
        String gender,
        RoleType roleType,
        UserStatus status // 상태의 위치를 조정
) {
    public static UserDto of(Long userId, String loginId, String password, String username, String email, Integer age, String gender, RoleType roleType, UserStatus status) {
        return new UserDto(userId, loginId, password, username, email, age, gender, roleType, status);
    }

    public static UserDto of(String loginId){
        return new UserDto(null, loginId, null, null, null, null, null, null, null);
    }

    public static UserDto of(Long userId, String loginId, String username, String password, String email, RoleType roleType) {
        return new UserDto(userId, loginId, password, username, email, null, null, roleType, null);
    }

    public static UserDto fromEntity(User entity) {
        return UserDto.of(
                entity.getId(),
                entity.getLoginId(),
                entity.getPassword(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getAge(),
                entity.getGender(),
                entity.getRoleType(),
                entity.getUserStatus()
        );
    }
}
