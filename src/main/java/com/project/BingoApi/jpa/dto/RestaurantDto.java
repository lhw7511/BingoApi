package com.project.BingoApi.jpa.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class RestaurantDto {

    private Long restaurantId;

    private CategoryDto category;

    private RegionDto region;

    private String name;

    private String address;

    private String phoneNumber;

    private String latitude;

    private String longitude;

    private String openTime;

    private List<ImageRestaurantDto> imagesRestaurants;

    private Double avgRating;

    private Long cnt;

    private String distanceGap;
    private String parkingYn;

    private List<FoodDto> foods;

    private List<ReviewDto> reviews;


    public RestaurantDto(Restaurant restaurant, Double avgRating, Long cnt, String distanceGap){
            this.restaurantId = restaurant.getId();
            this.name = restaurant.getName();
            this.address = restaurant.getAddress();
            this.phoneNumber = restaurant.getPhoneNumber();
            this.latitude = restaurant.getLatitude();
            this.longitude = restaurant.getLongitude();
            this.openTime = restaurant.getOpenTime();
            this.parkingYn = restaurant.getParkingYn();
            this.category = new CategoryDto(restaurant.getCategory());
            this.region = new RegionDto(restaurant.getRegion());
            this.avgRating = avgRating;
            this.cnt = cnt;
            this.distanceGap = distanceGap;
            restaurant.getImagesRestaurants().stream().forEach(r -> r.getImageUrl());
            imagesRestaurants = restaurant.getImagesRestaurants().stream().map(imageRestaurant -> new ImageRestaurantDto(imageRestaurant))
                    .collect(Collectors.toList());
    }

    public RestaurantDto(Restaurant restaurant, ReviewDto reviewDto) {
        this.restaurantId = restaurant.getId();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.phoneNumber = restaurant.getPhoneNumber();
        this.latitude = restaurant.getLatitude();
        this.longitude = restaurant.getLongitude();
        this.openTime = restaurant.getOpenTime();
        this.parkingYn = restaurant.getParkingYn();
        this.avgRating = Math.round(reviewDto.getAvgRating() * 100) / 100.0;
        this.cnt = reviewDto.getCnt();
        restaurant.getImagesRestaurants().stream().forEach(r -> r.getImageUrl());
        imagesRestaurants = restaurant.getImagesRestaurants().stream().map(imageRestaurant -> new ImageRestaurantDto(imageRestaurant))
                .collect(Collectors.toList());
    }
}
