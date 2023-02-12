package com.project.BingoApi.mybatis.mapper;

import com.project.BingoApi.mybatis.dto.MbRestaurantDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MbRestaurantMapper {

    public List<MbRestaurantDto> getTopAvgRestaurantList();

    public List<MbRestaurantDto> getTopCntRestaurantList();
}
