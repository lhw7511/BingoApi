package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Long, Restaurant> {

}
