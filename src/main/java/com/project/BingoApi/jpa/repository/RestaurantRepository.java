package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select r from Restaurant r")
    List<Restaurant> getList();

}
