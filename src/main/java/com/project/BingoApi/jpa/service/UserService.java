package com.project.BingoApi.jpa.service;


import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.dto.UserDto;
import com.project.BingoApi.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public UserDto findByEmail(String email){
        User user = userRepository.findByEmail(email);
        return  new UserDto(user);
    }

    public User joinUser(User user){
        return userRepository.save(user);
    }
}
