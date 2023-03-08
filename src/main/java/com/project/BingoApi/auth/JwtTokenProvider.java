package com.project.BingoApi.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class JwtTokenProvider {

    public static String createAccessToken(Long id, String email, Date expireDt){
        return JWT.create()
                .withSubject("ATK")
                .withExpiresAt(expireDt)
                .withClaim("id",id)
                .withClaim("email",email)
                .sign(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret")));
    }

    public static String createRefreshToken(Long id, String email, Date expireDt){

        return JWT.create()
                .withSubject("RTK")
                .withExpiresAt(expireDt)
                .withClaim("id",id)
                .withClaim("email",email)
                .sign(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret")));
    }
}
