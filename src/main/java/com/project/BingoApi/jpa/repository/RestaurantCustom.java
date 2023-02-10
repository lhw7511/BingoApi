package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.dto.RestaurantDto;

import java.util.List;

public interface RestaurantCustom {

    List<RestaurantDto> getTopAvgRatingList();

}
