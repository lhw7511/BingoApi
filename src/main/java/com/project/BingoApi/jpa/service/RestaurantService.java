package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.dto.MainParamDto;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.project.BingoApi.jpa.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;


    public Page<RestaurantDto> getMainList(MainParamDto mainParamDto, Pageable pageable){
        return restaurantRepository.getMainList(mainParamDto,pageable);
    }
}
