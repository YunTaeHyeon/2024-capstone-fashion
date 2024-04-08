package com.example.fashioncommuni.security.controller;

import com.example.fashioncommuni.member.dto.UserDto;
import com.example.fashioncommuni.security.handler.CustomAuthenticationProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.fashioncommuni.security.jwt.TokenUtils.generateJwtToken;

public class LoginController {
    private final CustomAuthenticationProvider customAuthenticationProvider;

    public LoginController(CustomAuthenticationProvider customAuthenticationProvider) {
        this.customAuthenticationProvider = customAuthenticationProvider;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * [Action] 로그인 프로세스를 동작시킨다.
     */
    @PostMapping("/user/login")
    public ResponseEntity<?> authenticateUser(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        // 사용자 인증을 위한 Authentication 객체 생성
        Authentication authentication   = new UsernamePasswordAuthenticationToken(username, password);

        // CustomAuthenticationProvider를 사용하여 사용자를 인증하고 결과를 받아옴
        Authentication result = customAuthenticationProvider.authenticate(authentication);

        // 인증 결과를 SecurityContext에 설정
        SecurityContextHolder.getContext().setAuthentication(result);

        // 성공적으로 인증되었을 경우 처리
        // 여기에서 세션을 설정하거나 JWT 토큰을 생성할 수 있음
        String token = generateJwtToken(UserDto.of(username)); // 사용자 이름을 기반으로 JWT 토큰 생성
        Cookie jwtCookie = new Cookie("jwt", token); // 생성한 토큰을 쿠키에 설정
        jwtCookie.setMaxAge(60 * 60 * 8); // 토큰 유효 기간 설정 (예: 8시간)
        jwtCookie.setPath("/"); // 쿠키 경로 설정
        response.addCookie(jwtCookie); // 쿠키를 응답 헤더에 추가

        return ResponseEntity.ok().build();
    }



    /**
     * [Action] 로그아웃 프로세스를 동작시킨다.
     * 이 로그아웃은 cookie 값을 삭제함으로써 jwt토큰을 없애서 동작하도록 구성하였다.
     */
    @GetMapping("/user/logout")
    public String logout(HttpServletResponse response) {
        // JWT 토큰을 저장하는 쿠키의 값을 삭제
       Cookie jwtCookie = new Cookie("jwt", null);
        jwtCookie.setMaxAge(0);  // 쿠키의 유효기간을 0으로 설정하여 즉시 삭제
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        return "redirect:/login";  // 로그인 페이지로 리다이렉트
    }
}