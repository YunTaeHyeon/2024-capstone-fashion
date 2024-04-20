package com.example.fashioncommuni.security.controller;

import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.dto.SecurityUserDetailsDto;
import com.example.fashioncommuni.member.service.SecurityUserService;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.service.UserCategoryScoresService;
import com.example.fashioncommuni.recommend.service.UserInitInterestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/main")
@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {
    private final SecurityUserService securityUserService;
    private final UserCategoryScoresService userCategoryScoresService;

    @GetMapping("/rootPage")
    public String rootPage(Authentication authentication, Model model) {

        SecurityUserDetailsDto userDetailsDto = (SecurityUserDetailsDto) authentication.getPrincipal();
        String authLoginId = userDetailsDto.getUsername();

        System.out.println("authLoginId = " + authLoginId);

        User user = securityUserService.findByLoginId(authLoginId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        try {
            UserCategoryScores userCategoryScores = userCategoryScoresService.findUserCategoryScoresByUserId(user.getId());
        } catch (Exception e) {
            log.info("초기 추천화면");
            log.info("user.getId() = " + user.getId());

            return "init-interest";
        }

        log.info("초기가 아닌 화면");
        return "rootPage";
    }
}
