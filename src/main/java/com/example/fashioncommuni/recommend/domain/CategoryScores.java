package com.example.fashioncommuni.recommend.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class CategoryScores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_category_score_id")
    private UserCategoryScores userCategoryScores;

    @Column(name = "category") // 카테고리를 나타내는 속성 추가
    private String category;

    @ElementCollection
    @CollectionTable(name = "score_values", joinColumns = @JoinColumn(name = "category_score_id"))
    @Column(name = "score")
    private List<Double> scores = new ArrayList<>();

    public void addCategoryScores(double score){
        scores.add(score);
    }
}
