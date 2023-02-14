package com.project.BingoApi.jpa.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.ImageRestaurant;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class ImageRestaurantDto {

    private Long imageRestaurantId;

    private String imageUrl;

    private String imageKey;
    public ImageRestaurantDto(ImageRestaurant imageRestaurant) {
        imageRestaurantId = imageRestaurant.getId();
        imageUrl = imageRestaurant.getImageUrl();
        imageKey = imageRestaurant.getImageKey();
    }
}
