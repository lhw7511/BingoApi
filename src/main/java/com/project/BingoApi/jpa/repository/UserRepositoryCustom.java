package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.project.BingoApi.jpa.dto.UserDto;

import java.util.List;

public interface UserRepositoryCustom {

    public UserDto getMyPage(String email);

    public List<RestaurantDto> getUserWishList(String email);
}
