package com.example.fashioncommuni.member.service;

import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.dto.UserDto;
import com.example.fashioncommuni.member.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j@RequiredArgsConstructor
@Service
public class SecurityUserServiceImpl implements SecurityUserService{
    private final UserRepository userRepository;

    // login하면 Optional<UserDto>를 반환한다.
    @Override
    public Optional<UserDto> login(UserDto userDto) {
        return userRepository.findUserByLoginId(userDto.loginId())
                .map(UserDto::fromEntity);
    }

    @Override
    public Optional<User> findByLoginId(String loginId) {
        return userRepository.findUserByLoginId(loginId);
    }
}
