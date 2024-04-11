package com.example.fashioncommuni.recommend.domain;

import com.example.fashioncommuni.member.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "user_category_scores")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
public class UserCategoryScores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_category_score_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    //@OneToOne
    //@JoinColumn(name = "category_id")
    //private Category category;
    //toDO: 카테고리 엔티티 만들기

    @ElementCollection
    @CollectionTable(name = "category_scores", joinColumns = @JoinColumn(name = "user_category_score_id"))
    @Column(name = "score")
    private List<Double> scores = new ArrayList<>(12);
    //코드 설명: 사용자의 카테고리별 선호도 점수를 저장
    //처음에는 12개의 카테고리에 대한 점수를 0으로 초기화

    @Builder
    public UserCategoryScores(Long id, User user, List<Double> scores) {
        this.id = id;
        this.user = user;
        this.scores = scores;
    }

    public static UserCategoryScores of(User user, List<Double> scores) {
        return new UserCategoryScores(null, user, scores);
    }

    public void updateScores(List<Double> scores) {
        this.scores = scores;
    }


}