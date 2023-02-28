package com.project.BingoApi.jpa.controller;

import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.dto.UserDto;
import com.project.BingoApi.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("join")
    public String join(UserDto userDto){
            User user = User.builder()
                    .fullName(userDto.getFullName())
                    .email(userDto.getEmail())
                    .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                    .roles("ROLE_USER")
                    .build();
            userRepository.save(user);
        return "1";
    }

}
