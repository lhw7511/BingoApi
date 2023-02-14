package com.project.BingoApi.jpa.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "restaurant_id")
    @Setter
    private Restaurant restaurant;

    @Column(name = "user_id")
    private String userId;


    @Column(columnDefinition = "TEXT")
    private String content;

    private Double rating;

    @OneToMany(mappedBy = "review",cascade = CascadeType.ALL)
    List<ImageReview> imageReviews = new ArrayList<>();



    public void setImageReviews(ImageReview imageReview){
        this.imageReviews.add(imageReview);
        imageReview.setReview(this);
    }
}
