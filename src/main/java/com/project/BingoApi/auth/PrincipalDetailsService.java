package com.project.BingoApi.auth;


import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User userEntity = userRepository.findByEmail(email);
        if(userEntity != null){
            return new PrincipalDetails(userEntity);
        }
        return Optional.ofNullable(userEntity).map(PrincipalDetails::new).orElseThrow(()->new UsernameNotFoundException(email));
    }
}
