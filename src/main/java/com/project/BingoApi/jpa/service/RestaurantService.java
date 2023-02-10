package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;


    public List<Restaurant> getRestaurantList(){
        return restaurantRepository.getList();
    }
}
