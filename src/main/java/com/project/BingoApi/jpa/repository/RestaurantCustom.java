package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.dto.MainParamDto;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantCustom {

    Page<RestaurantDto> getMainList(MainParamDto mainParamDto, Pageable pageable);

}
