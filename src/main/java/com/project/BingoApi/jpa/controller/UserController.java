package com.project.BingoApi.jpa.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.BingoApi.auth.JwtTokenInfo;
import com.project.BingoApi.auth.PrincipalDetails;
import com.project.BingoApi.jpa.domain.Restaurant;
import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.domain.WishList;
import com.project.BingoApi.jpa.dto.RestaurantDto;
import com.project.BingoApi.jpa.dto.UserDto;
import com.project.BingoApi.jpa.repository.RestaurantRepository;
import com.project.BingoApi.jpa.repository.UserRepository;
import com.project.BingoApi.jpa.service.RestaurantService;
import com.project.BingoApi.jpa.service.TokenService;
import com.project.BingoApi.jpa.service.UserService;
import com.project.BingoApi.jpa.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    private final TokenService tokenService;

    private final WishListService wishListService;

    private final RestaurantRepository restaurantRepository;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("join")
    public String join(UserDto userDto){
        String status  = "success";
        try{
            User user = User.builder()
                    .fullName(userDto.getFullName())
                    .email(userDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .roles("ROLE_USER")
                    .build();
            userService.joinUser(user);
        }catch (Exception e){
            status = "fail";
        }

        return status;
    }

    @RequestMapping("user/myPage")
    public UserDto myPage(Principal principal){
        return userService.getMyPage(principal.getName());
    }


    @RequestMapping("user/logout")
    public String logOut(Principal principal){
        String status  = "success";
        try {
            UserDto userInfo = userService.findByEmail(principal.getName());
            tokenService.deleteRefreshToken(userInfo.getUserId());
        }catch (Exception e){
            status = "fail";
        }
        return status;
    }

    @RequestMapping("user/saveWishList")
    public void saveWishList(Principal principal, Long restaurantId){

        try {
            wishListService.save(principal.getName(),restaurantId);
        } catch (Exception e) {
            throw new IllegalStateException("등록 실패");
        }

    }

    @RequestMapping("user/deleteWishList")
    public void deleteWishList(Principal principal, Long restaurantId){
        try {
            wishListService.deleteWishListOne(principal.getName(), restaurantId);
        } catch (Exception e) {
            throw new IllegalStateException("삭제 실패");
        }
    }

    @RequestMapping("user/userWishList")
    public List<RestaurantDto> userWishList(Principal principal){
            return userService.getUserWishList(principal.getName());
    }

}
