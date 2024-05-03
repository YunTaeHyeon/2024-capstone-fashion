package com.example.fashioncommuni.recommend.controller;

import com.example.fashioncommuni.board.service.PostService;
import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.dto.SecurityUserDetailsDto;
import com.example.fashioncommuni.member.service.SecurityUserService;
import com.example.fashioncommuni.recommend.domain.UserCategoryScores;
import com.example.fashioncommuni.recommend.service.UserCategoryScoresService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class RecommendPostController {

    private final PostService postService;
    private final SecurityUserService securityUserService;
    private final UserCategoryScoresService userCategoryScoresService;

    @GetMapping("/home")
    public String home(Authentication authentication , Model model, Pageable pageable, String keyword) { // @PageableDefault(page = 0, size = 10, sort = "post_id", direction = Sort.Direction.DESC)

        SecurityUserDetailsDto userDetailsDto = (SecurityUserDetailsDto) authentication.getPrincipal();
        String authLoginId = userDetailsDto.getUsername();

        User user = securityUserService.findByLoginId(authLoginId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        try {
            UserCategoryScores userCategoryScores = userCategoryScoresService.findUserCategoryScoresByUserId(user.getId());
        } catch (Exception e) {
            return "init-interest";
        }

        if(keyword == null) {
            model.addAttribute("postList", postService.postList(pageable));
        } else {
            model.addAttribute("postList", postService.searchingPostList(keyword, pageable));
        }

        return "home";
    }
}
