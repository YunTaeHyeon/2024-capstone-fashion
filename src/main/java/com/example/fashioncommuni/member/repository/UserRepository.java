package com.example.fashioncommuni.member.repository;

import com.example.fashioncommuni.member.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findUserByLoginId(String loginId);

    /**
     * 이메일로 회원 찾기
     * @param email 회원 이메일
     * @return 회원 정보
     */
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
