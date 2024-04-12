package com.example.fashioncommuni.recommend.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CategoryScores {
    //@Id
    //@Column(name = "category_score_id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_category_score_id")
    private UserCategoryScores userCategoryScores;

    @Column(name = "category") // 카테고리를 나타내는 속성 추가
    private String category;

    @ElementCollection
    @CollectionTable(name = "score_values", joinColumns = @JoinColumn(name = "category_score_id"))
    @Column(name = "score")
    private List<Double> scores = new ArrayList<>();

    @Builder
    public CategoryScores(UserCategoryScores userCategoryScores, String category, List<Double> scores) {
        this.userCategoryScores = userCategoryScores;
        this.category = category;
        this.scores = scores;
    }

    public void addCategoryScores(double score){
        scores.add(score);
    }
}