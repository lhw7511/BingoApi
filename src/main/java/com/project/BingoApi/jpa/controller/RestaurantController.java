package com.project.BingoApi.jpa.controller;

import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.project.BingoApi.jpa.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("restaurantList")
    public List<RestaurantDto> getList(){
        return restaurantService.getTopAvgRatingList();
    }
}
