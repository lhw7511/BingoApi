package com.project.BingoApi.jpa.controller;

import com.project.BingoApi.jpa.dto.ParamDto;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.project.BingoApi.jpa.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class RestaurantController {

    private final RestaurantService restaurantService;

    //메인화면 목록
    @RequestMapping("mainList")
    public Page<RestaurantDto> getMainList(ParamDto mainParamDto){
        return restaurantService.getMainList(mainParamDto);
    }

    //컨텐츠 상세화면
    @RequestMapping("restaurantOne")
    public RestaurantDto getRestaurantOne(Long id){
        return restaurantService.getRestaurantOne(id);
    }
}
