package com.example.fashioncommuni.recommend.controller;

import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.dto.SecurityUserDetailsDto;
import com.example.fashioncommuni.member.service.SecurityService;
import com.example.fashioncommuni.member.service.SecurityUserService;
import com.example.fashioncommuni.member.service.UserService;
import com.example.fashioncommuni.recommend.service.UserInitInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/init-interest")
public class UserInitInterestController {

    private final UserInitInterestService userInitInterestService;
    private final SecurityUserService securityUserService;

    @GetMapping("/{loginId})")
    public String ViewInitInterestPage(@PathVariable String loginId, Model model, Authentication authentication) {

        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        String authLoginId = userDetails.getUsername();

        if(loginId != authLoginId) {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        }

        User user = securityUserService.findByLoginId(loginId).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        model.addAttribute("userId", user.getId());

        return "init-interest";

    }

    @PostMapping("/{userId}/{categoryId}")
    public void initInterest(@PathVariable Long userId, @PathVariable Long categoryId, Authentication authentication) {

        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        String loginId = userDetails.getUsername();

        User user = securityUserService.findByLoginId(loginId).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        if(user.getId() != userId) {
            throw new IllegalArgumentException("해당 유저가 존재하지 않습니다.");
        }

        userInitInterestService.selectImageForInitInterest(user.getId(), categoryId);
    }

}
