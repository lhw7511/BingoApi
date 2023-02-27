package com.project.BingoApi.jpa.controller;

import com.project.BingoApi.jpa.dto.FoodDto;
import com.project.BingoApi.jpa.dto.ParamDto;
import com.project.BingoApi.jpa.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mobile")
public class FoodController {

    private final FoodService foodService;

    @RequestMapping("foodList")
    public Page<FoodDto> getFoodList(ParamDto paramDto){
        return foodService.getFoodList(paramDto);
    }

}
