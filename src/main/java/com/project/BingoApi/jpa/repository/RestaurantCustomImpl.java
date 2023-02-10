package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.QImageRestaurant;
import com.project.BingoApi.jpa.domain.QRestaurant;
import com.project.BingoApi.jpa.domain.QReview;
import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.BingoApi.jpa.domain.QImageRestaurant.*;
import static com.project.BingoApi.jpa.domain.QRestaurant.restaurant;
import static com.project.BingoApi.jpa.domain.QReview.review;

@RequiredArgsConstructor
public class RestaurantCustomImpl implements RestaurantCustom{

    private  final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<RestaurantDto> getTopAvgRatingList() {
        List<Restaurant> restaurantList = jpaQueryFactory.select(restaurant
                )
                .from(restaurant)
                .join(restaurant.reviews, review)
                .groupBy(restaurant.id)
                .orderBy(review.rating.avg().desc())
                .limit(4)
                .fetch();
        List<RestaurantDto> avgRatingList = jpaQueryFactory.select(Projections.bean(RestaurantDto.class, review.rating.avg().as("avgRating"))
                )
                .from(restaurant)
                .join(restaurant.reviews, review)
                .groupBy(restaurant.id)
                .orderBy(review.rating.avg().desc())
                .limit(4)
                .fetch();
        List<RestaurantDto>  restaurantDtos = restaurantList.stream().map(r -> new RestaurantDto(r)).collect(Collectors.toList());
        for(int i = 0; i < restaurantDtos.size(); i++){
            restaurantDtos.get(i).setAvgRating(avgRatingList.get(0).getAvgRating());
        }
        return restaurantDtos;
    }
}
