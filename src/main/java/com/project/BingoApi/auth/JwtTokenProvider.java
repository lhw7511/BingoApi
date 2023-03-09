package com.project.BingoApi.auth;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

@Slf4j
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

    public static String validToken(String jwtToken, HttpServletRequest request){
        String email = null;
        try{
            email = JWT.require(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret"))).build().verify(jwtToken).getClaim("email").asString();
        }catch (SignatureVerificationException | SignatureGenerationException e) {
            log.info("잘못된 JWT 서명입니다.");
            setRequest(request,"SignatureException");
        } catch (TokenExpiredException e) {
            log.info("만료된 JWT 토큰입니다.");
            setRequest(request,"TokenExpiredException");
        } catch (IllegalArgumentException | JWTVerificationException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            setRequest(request,"VerificationException");
        } catch (Exception e){
            log.info(e.getMessage());
            setRequest(request,"Exception");
        }
        return email;
    }

    public static void setRequest(HttpServletRequest request, String errMsg) {
       request.setAttribute("exception",errMsg);
    }
}
