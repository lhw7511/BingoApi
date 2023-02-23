package com.project.BingoApi.jpa.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.ImageReview;
import com.project.BingoApi.jpa.domain.Review;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import static javax.persistence.FetchType.LAZY;
@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class ImageReviewDto {

    private Long imageReviewId;


    private String imageUrl;

    private String imageKey;

    public ImageReviewDto(ImageReview imageReview) {
        this.imageReviewId = imageReview.getId();
        this.imageKey = imageReview.getImageKey();
        this.imageUrl = imageReview.getImageUrl();

    }
}
