package com.project.BingoApi.jpa.repository;


import com.project.BingoApi.jpa.domain.Food;
import com.project.BingoApi.jpa.domain.Review;
import com.project.BingoApi.jpa.dto.ReviewDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select rv from Review rv where rv.restaurant.id =:restaurantId  order by rv.id desc")
    List<Review> getTop3Review(@Param("restaurantId") Long restaurantId, Pageable pageable);

    @Query("select new com.project.BingoApi.jpa.dto.ReviewDto(avg(rv.rating),count(*)) from Review rv where rv.restaurant.id =:restaurantId")
    ReviewDto getCntAndRatingOne(@Param("restaurantId") Long restaurantId);
}
