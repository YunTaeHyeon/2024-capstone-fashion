package com.example.fashioncommuni.member.service;

import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.dto.UserDto;
import com.example.fashioncommuni.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public void registerUser(UserDto userDto) {
        if (userRepository.findByEmail(userDto.email()).isPresent()) {
            throw new RuntimeException("이미 이메일이 존재합니다.");
        }
        if (userRepository.findUserByLoginId(userDto.loginId()).isPresent()) {
            throw new RuntimeException("이미 로그인 아이디가 존재합니다.");
        }

        String encodedPassword = passwordEncoder.encode(userDto.password());

        UserDto encryptedUserDto = new UserDto(
                userDto.userId(),
                userDto.loginId(),
                encodedPassword,
                userDto.username(),
                userDto.email(),
                userDto.age(),
                userDto.gender(),
                userDto.roleType(),
                userDto.status()
        );

        User user = User.of(encryptedUserDto);
        userRepository.save(user);
    }
}
