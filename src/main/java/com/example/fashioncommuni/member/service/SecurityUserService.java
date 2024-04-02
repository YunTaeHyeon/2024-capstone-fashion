package com.example.fashioncommuni.member.service;

import com.example.fashioncommuni.member.dto.UserDto;

import java.util.Optional;

public interface SecurityUserService {
    Optional<UserDto> login(UserDto userDto);
}
