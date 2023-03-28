package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.domain.WishList;
import com.project.BingoApi.jpa.repository.RestaurantRepository;
import com.project.BingoApi.jpa.repository.UserRepository;
import com.project.BingoApi.jpa.repository.WishListRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishListService {

    private final WishListRepository wishListRepository;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    public void save(String email, Long restaurantId) throws  Exception{
        User userInfo = userRepository.findByEmail(email);
        Optional<Restaurant> restaurantOne = restaurantRepository.findById(restaurantId);
        if(restaurantOne.isPresent()){
            WishList getOne = getWishListOne(userInfo.getId(), restaurantId);
            if(getOne != null){
                throw  new IllegalStateException("이미 등록된 위시리스트입니다");
            }
            WishList wishList = WishList.createWishList(userInfo, restaurantOne.get());
            wishListRepository.save(wishList);
        }

    }

    public WishList getWishListOne(Long userId, Long restaurantId){
        return wishListRepository.getWishListOne(userId,restaurantId);
    }

    public void deleteWishListOne(String email, Long restaurantId) throws  Exception{
        User userInfo = userRepository.findByEmail(email);
        WishList getOne = getWishListOne(userInfo.getId(), restaurantId);
        wishListRepository.delete(getOne);
    }
}
