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

    //private Long categoryId; -> 없어도 될 듯

    @ElementCollection
    @CollectionTable(name = "score_values", joinColumns = @JoinColumn(name = "category_score_id"))
    @Column(name = "score")
    private List<Double> scores = new ArrayList<>();

    public void addCategoryScores(double score){
        scores.add(score);
    }
}
