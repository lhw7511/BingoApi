package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.domain.Food;
import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.domain.Review;
import com.project.BingoApi.jpa.dto.FoodDto;
import com.project.BingoApi.jpa.dto.MainParamDto;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.project.BingoApi.jpa.dto.ReviewDto;
import com.project.BingoApi.jpa.repository.FoodRepository;
import com.project.BingoApi.jpa.repository.RestaurantRepository;
import com.project.BingoApi.jpa.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ReviewRepository reviewRepository;
    private final FoodRepository foodRepository;

    public Page<RestaurantDto> getMainList(MainParamDto mainParamDto){
        PageRequest pageRequest = PageRequest.of(mainParamDto.getCurPage()-1,4);
        return restaurantRepository.getMainList(mainParamDto,pageRequest);
    }

    public RestaurantDto getRestaurantOne(Long id){
        PageRequest pageRequest = PageRequest.of(0,3);
        Optional<Restaurant> tmpRestaurant = restaurantRepository.findById(id);
        Restaurant restaurant;

        if(tmpRestaurant.isPresent()){
            restaurant = tmpRestaurant.get();
            List<Review> top3Review = reviewRepository.getTop3Review(id,pageRequest);
            List<FoodDto> top3Menu = foodRepository.getTop3Menu(id,pageRequest);
            ReviewDto cntAndRatingOne = reviewRepository.getCntAndRatingOne(id);
            RestaurantDto result = new RestaurantDto(
                    restaurant,cntAndRatingOne
                        );
            result.setFoods(top3Menu);
            result.setReviews(top3Review.stream().map(t3r->new ReviewDto(t3r)).collect(Collectors.toList()));

            return result;
        }

        return null;
    }
}
