package com.example.fashioncommuni.member.domain;

import com.example.fashioncommuni.member.dto.UserDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Entity
@Table(name ="USERS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(callSuper = true)
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
    @Builder
    public User(Long id,String loginId, String password, String username, String email, Integer age, String gender, RoleType roleType, UserStatus userStatus) {
        this.id=id;
        this.loginId = loginId;
        this.password = password;
        this.username = username;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.roleType = roleType;
        this.userStatus = userStatus;
    }
    public static User of(String loginId, String password, String username, String email, Integer age, String gender,RoleType roleType, UserStatus userStatus) {
        return new User(null, loginId, password, username, email, age, gender, roleType, userStatus);
    }
    public static User of(UserDto userDto) {
        return User.builder()
                .id(userDto.userId())
                .loginId(userDto.loginId())
                .password(userDto.password())
                .email(userDto.email())
                .age(userDto.age())
                .gender(userDto.gender())
                .roleType(userDto.roleType())
                .userStatus(userDto.status())
                .build();
    }

    // dto -> entity 변환 메서드
    public static User fromDto(UserDto dto) {
        return new User(
                dto.userId(),
                dto.loginId(),
                dto.password(),
                dto.username(),
                dto.email(),
                dto.age(),
                dto.gender(),
                dto.roleType(),
                dto.status()
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return this.id != null && Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
