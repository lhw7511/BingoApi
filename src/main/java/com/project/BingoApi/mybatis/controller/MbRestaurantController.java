package com.project.BingoApi.mybatis.controller;

import com.project.BingoApi.mybatis.dto.MbRestaurantDto;
import com.project.BingoApi.mybatis.service.MbRestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MbRestaurantController {

    private final MbRestaurantService mbRestaurantService;

    @RequestMapping("mainList")
    public HashMap<String,List<MbRestaurantDto>> mainList() throws  Exception{
        return mbRestaurantService.getMainList();
    }
}
