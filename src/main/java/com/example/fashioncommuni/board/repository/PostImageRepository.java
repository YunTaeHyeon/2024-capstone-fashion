package com.example.fashioncommuni.board.repository;

import com.example.fashioncommuni.board.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {
}