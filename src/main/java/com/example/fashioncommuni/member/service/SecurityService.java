package com.example.fashioncommuni.member.service;

import com.example.fashioncommuni.member.domain.User;
import com.example.fashioncommuni.member.repository.UserRepository;
import com.example.fashioncommuni.security.exception.ErrorCode;
import com.example.fashioncommuni.security.exception.ProfileApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {
    private final UserRepository userRepository;
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    /**
     * 유저이름으로 유저를 꺼낸다.
     */
    public User getCurrentUser() {
        String username = getCurrentUsername();

        if (username != null) {
            return userRepository.findUserByLoginId(username)
                    .orElseThrow(() -> new ProfileApplicationException(ErrorCode.USER_NOT_FOUND));
        }

        return null;
    }
}
