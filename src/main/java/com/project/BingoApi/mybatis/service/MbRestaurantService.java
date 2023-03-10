package com.project.BingoApi.mybatis.service;


import com.project.BingoApi.mybatis.dto.MbRestaurantDto;
import com.project.BingoApi.mybatis.mapper.MbRestaurantMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MbRestaurantService {

    private  final MbRestaurantMapper mbRestaurantMapper;


    public HashMap<String,Object> getMainList(){
        HashMap<String,Object> result = new HashMap<>();
        result.put("topAvg",mbRestaurantMapper.getTopAvgRestaurantList());
        result.put("topCount",mbRestaurantMapper.getTopCntRestaurantList());
        result.put("topRegion",mbRestaurantMapper.getTopRegionList());
        return result;
    }
}
