package com.project.BingoApi.jpa.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class ReviewDto {

    private Long reviewId;

    private Restaurant restaurant;

    private String content;

    private Double rating;

    private Double avgRating;

    private Long cnt;

    private String username;

    private String userProfileUrl;
    private List<ImageReviewDto> imageReviewDtos = new ArrayList<>();


    public ReviewDto(Double avgRating, Long cnt) {
        this.avgRating = avgRating;
        this.cnt = cnt;
    }

    public ReviewDto(Review review) {
        this.reviewId  = review.getId();
        this.username = review.getUser().getFullName();
        this.userProfileUrl = review.getUser().getProfileUrl();
        this.rating = review.getRating();
        this.content = review.getContent();
        review.getImageReviews().stream().forEach(rv -> rv.getImageUrl());
        imageReviewDtos = review.getImageReviews().stream().map(rv -> new ImageReviewDto(rv)).collect(Collectors.toList());
    }
}
