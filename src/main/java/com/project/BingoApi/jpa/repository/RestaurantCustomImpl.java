package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.QRestaurant;
import com.project.BingoApi.jpa.domain.Restaurant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.project.BingoApi.jpa.domain.QRestaurant.restaurant;

@RequiredArgsConstructor
public class RestaurantCustomImpl implements RestaurantCustom{

    private  final JPAQueryFactory jpaQueryFactory;

}
