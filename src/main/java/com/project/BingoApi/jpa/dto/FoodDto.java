package com.project.BingoApi.jpa.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BingoApi.jpa.domain.Food;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Null 값인 필드 제외
public class FoodDto {

    private Long foodId;

    private String foodName;

    private Long price;


    public FoodDto(Long id, String name, Long price) {
        this.foodId = id;
        this.foodName = name;
        this.price = price;
    }

    public FoodDto(Food food) {
        this.foodId = food.getId();
        this.foodName = food.getName();
        this.price = food.getPrice();
    }
}
