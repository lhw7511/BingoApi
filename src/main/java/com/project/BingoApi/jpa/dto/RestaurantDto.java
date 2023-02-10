package com.project.BingoApi.jpa.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.BingoApi.jpa.domain.ImageRestaurant;
import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.domain.Review;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class RestaurantDto {

    private Long restaurantId;

    private String lmsKey;

    private String name;

    private String address;

    private String phoneNumber;

    private String coordinate;

    private String openTime;

    private List<ImageRestaurantDto> imagesRestaurants = new ArrayList<>();


    public RestaurantDto(Restaurant restaurant) {
        restaurantId = restaurant.getId();
        name = restaurant.getName();
        lmsKey = restaurant.getLmsKey();
        address = restaurant.getAddress();
        phoneNumber = restaurant.getPhoneNumber();
        coordinate = restaurant.getCoordinate();
        openTime = restaurant.getOpenTime();
        restaurant.getImagesRestaurants().stream().forEach(r -> r.getImageUrl());
        imagesRestaurants = restaurant.getImagesRestaurants().stream().map(imageRestaurant -> new ImageRestaurantDto(imageRestaurant))
                .collect(Collectors.toList());
    }
}
