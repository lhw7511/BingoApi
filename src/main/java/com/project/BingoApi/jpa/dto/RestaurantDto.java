package com.project.BingoApi.jpa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;

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

    private List<ImageRestaurantDto> imagesRestaurants = new ArrayList<>();

    private Double avgRating;

    private Long cnt;




    public RestaurantDto(Restaurant restaurant, Double avgRating, Long cnt){
            this.restaurantId = restaurant.getId();
            this.name = restaurant.getName();
            this.address = restaurant.getAddress();
            this.phoneNumber = restaurant.getPhoneNumber();
            this.latitude = restaurant.getLatitude();
            this.longitude = restaurant.getLongitude();
            this.openTime = restaurant.getOpenTime();
            this.category = new CategoryDto(restaurant.getCategory());
            this.region = new RegionDto(restaurant.getRegion());
            this.avgRating = avgRating;
            this.cnt = cnt;
            restaurant.getImagesRestaurants().stream().forEach(r -> r.getImageUrl());
            imagesRestaurants = restaurant.getImagesRestaurants().stream().map(imageRestaurant -> new ImageRestaurantDto(imageRestaurant))
                    .collect(Collectors.toList());
    }

}
