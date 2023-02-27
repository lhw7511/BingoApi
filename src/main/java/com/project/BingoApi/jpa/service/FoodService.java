package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.domain.Food;
import com.project.BingoApi.jpa.dto.FoodDto;
import com.project.BingoApi.jpa.dto.ParamDto;
import com.project.BingoApi.jpa.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FoodService {

    private final FoodRepository foodRepository;

    public Page<FoodDto> getFoodList(ParamDto paramDto){
        PageRequest pageRequest = PageRequest.of(paramDto.getCurPage() - 1, 4);
        Page<Food> foodList = foodRepository.getFoodList(paramDto.getRestaurantId(), pageRequest);
        return foodList.map(f -> new FoodDto(f));

    }
}
