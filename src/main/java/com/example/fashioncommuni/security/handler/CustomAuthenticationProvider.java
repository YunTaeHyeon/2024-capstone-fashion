package com.example.fashioncommuni.security.handler;

import com.example.fashioncommuni.member.dto.SecurityUserDetailsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j @RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;




    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("2.CustomAuthenticationProvider");

        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        // AuthenticationFilter에서 생성된 토큰으로부터 ID, PW를 조회
        String loginId = token.getName();
        String userPassword = (String) token.getCredentials();
        log.info(loginId);
        log.info(userPassword);

        // Spring security - UserDetailsService를 통해 DB에서 username으로 사용자 조회
        SecurityUserDetailsDto securityUserDetailsDto = (SecurityUserDetailsDto) userDetailsService.loadUserByUsername(loginId);

        // 대소문자를 구분하는 matches() 메서드로 db와 사용자가 제출한 비밀번호를 비교
        if (!passwordEncoder.matches(userPassword, securityUserDetailsDto.getUserDto().password())) {
            throw new BadCredentialsException(securityUserDetailsDto.getUsername() + "Invalid password");
        }
        // 인증이 성공하면 인증된 사용자의 정보와 권한을 담은 새로운 UsernamePasswordAuthenticationToken을 반환한다.
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(securityUserDetailsDto, userPassword, securityUserDetailsDto.getAuthorities());
        return authToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
