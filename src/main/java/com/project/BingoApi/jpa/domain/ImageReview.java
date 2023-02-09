package com.project.BingoApi.jpa.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class ImageReview {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_review_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(name = "image_url")
    private String imageUrl;

    public void setReview(Review review){
        this.review = review;
        review.getImageReviews().add(this);
    }
}
