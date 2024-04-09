package com.example.fashioncommuni.board.repository;

import com.example.fashioncommuni.board.domain.Image;
import com.example.fashioncommuni.member.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Image findByUser(User user);
}