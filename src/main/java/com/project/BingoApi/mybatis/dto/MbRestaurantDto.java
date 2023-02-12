package com.project.BingoApi.mybatis.dto;

import com.project.BingoApi.jpa.dto.ImageRestaurantDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MbRestaurantDto {

    private Long restaurantId;

    private String lmsKey;

    private String name;

    private String address;

    private String phoneNumber;

    private String coordinate;

    private String openTime;


    //리뷰평균평점
    private Double avgRating;

    //리뷰수
    private Integer cnt;

    private List<MbImageRestaurantDto> imagesRestaurants = new ArrayList<>();


}
