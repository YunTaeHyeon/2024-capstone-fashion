package com.example.fashioncommuni.board.controller;

import com.example.fashioncommuni.board.DTO.image.ImageUploadDTO;
import com.example.fashioncommuni.board.service.ImageService;
import com.example.fashioncommuni.member.dto.SecurityUserDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    /**
     * 프로필 사진 등록
     * @param imageUploadDTO 사진 정보
     * @param authentication 유저 정보
     * @return 프로필 페이지
     */
    @PostMapping("/upload")
    public String upload(@ModelAttribute ImageUploadDTO imageUploadDTO, Authentication authentication) {
        SecurityUserDetailsDto userDetails = (SecurityUserDetailsDto) authentication.getPrincipal();
        imageService.upload(imageUploadDTO, userDetails.getEmail());

        return "redirect:/member/info";
    }
}