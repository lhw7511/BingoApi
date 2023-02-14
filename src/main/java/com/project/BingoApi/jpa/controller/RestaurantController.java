package com.project.BingoApi.jpa.controller;

import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.dto.MainParamDto;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.project.BingoApi.jpa.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("mainList")
    public Page<RestaurantDto> getMainList(MainParamDto mainParamDto){
        PageRequest pageRequest = PageRequest.of(mainParamDto.getCurPage()-1,4);
        return restaurantService.getMainList(mainParamDto,pageRequest);
    }
}
