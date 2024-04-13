package com.example.fashioncommuni.member.controller;

import com.example.fashioncommuni.member.dto.UserDto;
import com.example.fashioncommuni.member.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return ResponseEntity.ok().build();
    }
}
