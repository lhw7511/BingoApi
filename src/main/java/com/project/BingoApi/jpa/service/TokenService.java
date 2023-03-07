package com.project.BingoApi.jpa.service;

import com.project.BingoApi.jpa.domain.RefreshToken;
import com.project.BingoApi.jpa.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;


    public void saveRefreshToken(Long id, String tokenName, Date expireDt){
        RefreshToken getOne = tokenRepository.findByUserId(id);
        if(getOne != null){
            getOne.updateTokenName(tokenName, expireDt);
        }else{
            RefreshToken refreshTokenEntity = RefreshToken.builder()
                    .userId(id)
                    .tokenName(tokenName)
                    .expireDt(expireDt)
                    .build();
            tokenRepository.save(refreshTokenEntity);
        }
    }
}
