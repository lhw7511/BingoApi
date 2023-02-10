package com.project.BingoApi.jpa.dto;


import com.project.BingoApi.jpa.domain.ImageRestaurant;
import lombok.Data;

import javax.persistence.Column;

@Data
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
