package com.project.BingoApi.jpa.repository;

import com.project.BingoApi.jpa.dto.UserDto;

public interface UserRepositoryCustom {

    public UserDto getMyPage(String email);
}
