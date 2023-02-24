package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.Food;
import com.project.BingoApi.jpa.dto.FoodDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    @Query("select f from Food f where f.restaurant.id =:restaurantId order by f.price asc")
    List<Food> getTop3Menu(@Param("restaurantId") Long restaurantId, Pageable pageable);
}
