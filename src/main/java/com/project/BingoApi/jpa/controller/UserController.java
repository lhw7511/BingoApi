package com.project.BingoApi.jpa.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.BingoApi.auth.JwtTokenInfo;
import com.project.BingoApi.auth.PrincipalDetails;
import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.dto.UserDto;
import com.project.BingoApi.jpa.repository.UserRepository;
import com.project.BingoApi.jpa.service.UserService;
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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("join")
    public String join(UserDto userDto){
            User user = User.builder()
                    .fullName(userDto.getFullName())
                    .email(userDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .roles("ROLE_USER")
                    .build();
        userService.joinUser(user);
        return "1";
    }

    @RequestMapping("user/myPage")
    public UserDto myPage(Principal principal){
        UserDto userInfo = userService.findByEmail(principal.getName());
        return userInfo;
    }

}
