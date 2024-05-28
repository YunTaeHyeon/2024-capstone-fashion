package com.example.fashioncommuni.recommend.domain;

import com.example.fashioncommuni.member.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Getter
@Entity
@Table(name = "user_category_scores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserCategoryScores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_category_score_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userCategoryScores", orphanRemoval = true)
    @MapKey(name = "category")
    private Map<String, CategoryScores> scores = new LinkedHashMap<>();
    //코드 설명: 사용자의 카테고리별 별점 점수를 저장
    //Long -> categoryId   CategoryScores -> 별점들을 저장


    @Builder
    public UserCategoryScores(Long id, User user, Map<String, CategoryScores> scores) {
        this.id = id;
        this.user = user;
        this.scores = scores;
    }

    public static UserCategoryScores of(User user, Map<String, CategoryScores> scores) {
        return new UserCategoryScores(null, user, scores);
    }

}