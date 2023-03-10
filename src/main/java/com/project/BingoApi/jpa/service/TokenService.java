package com.project.BingoApi.jpa.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.project.BingoApi.auth.JwtTokenInfo;
import com.project.BingoApi.auth.JwtTokenProvider;
import com.project.BingoApi.jpa.domain.RefreshToken;
import com.project.BingoApi.jpa.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TokenService {

    private final TokenRepository tokenRepository;


    public RefreshToken findById(Long userId){
        return tokenRepository.findByUserId(userId);
    }

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

    public String ValidRefreshAndReIssuanceAccessToken(String refreshHeader){
        String accessToken = "";
        if(refreshHeader == null || !refreshHeader.startsWith(JwtTokenInfo.getInfoByKey("prefix"))){
            return accessToken;
        }
        try{
            String reFreshToken = refreshHeader.replace(JwtTokenInfo.getInfoByKey("prefix"),"");
            Long id = JWT.require(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret"))).build().verify(reFreshToken).getClaim("id").asLong();
            String email = JWT.require(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret"))).build().verify(reFreshToken).getClaim("email").asString();
            Date accessTokenExpireDt = new Date(System.currentTimeMillis() + 60000);
            if(id != null && StringUtils.hasLength(email)){
                RefreshToken tokenInfo = tokenRepository.findByUserId(id);
                if(reFreshToken.equals(tokenInfo.getTokenName())){
                    accessToken = JwtTokenProvider.createAccessToken(id,email,accessTokenExpireDt);
                }
            }
        } catch (SignatureVerificationException | SignatureGenerationException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (TokenExpiredException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (IllegalArgumentException | JWTVerificationException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        } catch (Exception e){
            log.info(e.getMessage());
        }

        return accessToken;
    }
}
