package com.project.BingoApi.mybatis.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.dto.ImageRestaurantDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
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

    private MbRegionDto region;

    private List<MbImageRestaurantDto> imagesRestaurants = new ArrayList<>();


}
