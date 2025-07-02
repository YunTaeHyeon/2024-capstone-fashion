package com.example.fashioncommuni.recommend.controller;

import com.example.fashioncommuni.board.domain.Post;
import com.example.fashioncommuni.board.service.PostService;
import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.dto.SecurityUserDetailsDto;
import com.example.fashioncommuni.member.service.SecurityService;
import com.example.fashioncommuni.member.service.SecurityUserService;
import com.example.fashioncommuni.member.service.UserService;
import com.example.fashioncommuni.recommend.domain.CategoryScores;
import com.example.fashioncommuni.recommend.service.UserInitInterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/init-interest")
public class UserInitInterestController {

    private final UserInitInterestService userInitInterestService;
    private final SecurityUserService securityUserService;
    private final PostService postService;

    /* //이거 안쓸듯?
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
    */

    @PostMapping("/selectImage")
    public ResponseEntity<?> initInterest(@RequestBody String categoryId, Authentication authentication) {

        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        String loginId = userDetails.getUsername();

        User user = securityUserService.findByLoginId(loginId).orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));

        userInitInterestService.selectImageForInitInterest(user.getId(), categoryId);

        return ResponseEntity.ok().build();
    }

//    @PostMapping("/complete") //toDo: 이거 왜 안됨?
//    public String completeInitInterest(Authentication authentication, Model model, Pageable pageable, String keyword, Long categoryId) {
//
//        SecurityUserDetailsDto userDetailsDto = (SecurityUserDetailsDto) authentication.getPrincipal();
//        String authLoginId = userDetailsDto.getUsername();
//
//        User user = securityUserService.findByLoginId(authLoginId).orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않습니다."));
//
//        if(keyword != null) {
//            model.addAttribute("postList", postService.searchingPostList(keyword, pageable));
//        } else if (categoryId != null) {
//            model.addAttribute("postList", postService.searchingPostCategory(categoryId, pageable));
//        } else {
//            model.addAttribute("postList", postService.postList(pageable));
//        }
//
//        return "home";
//    }

}
