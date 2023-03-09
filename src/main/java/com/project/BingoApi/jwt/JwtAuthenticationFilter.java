package com.project.BingoApi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.BingoApi.auth.JwtTokenInfo;
import com.project.BingoApi.auth.JwtTokenProvider;
import com.project.BingoApi.auth.PrincipalDetails;
import com.project.BingoApi.jpa.domain.RefreshToken;
import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


//원래는 /login 요청해서 username,password를 post로전송하면 호출됨 근데 form로그인 disable해서안됨
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    private final TokenService tokenService;


    // /login 요청을하면 로그인 시도를 위해서 실행됨
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // username,password 받아서 정상 계정인지 조회
        //authenticationManager로 시도하면 PrincipalDetailService의 loadUserByUsername 함수 실행됨
        //principaldetails를 세션에담아서 jwt리턴


        ObjectMapper om = new ObjectMapper();
        User user = null;
        try {
            user = om.readValue(request.getInputStream(), User.class);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());

            //principaldetailsservice loadUserByUsername함수가실행됨
            //password는 스프링에서 알아서 처리해줌
            Authentication authentication = authenticationManager.authenticate(token);

            //세션에 authentication저장
            return authentication;
        } catch (Exception e) {
            logger.info("login exception");
            throw new UsernameNotFoundException("잘못된 정보입니다.");
        }

    }

    //attemptAuthentication이후 정상인증시 실행됨
    //jwt토큰 생성후 클라이언트에게 jwt토큰을 응답해줌
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        Date accessTokenExpireDt = new Date(System.currentTimeMillis() + 60000);
        Date refreshTokenExpireDt = new Date(System.currentTimeMillis() + Integer.parseInt(JwtTokenInfo.getInfoByKey("refreshExpiration")));


        String token = JwtTokenProvider.createAccessToken(principalDetails.getUser().getId(),principalDetails.getUser().getEmail(),accessTokenExpireDt);
        String refreshToken = JwtTokenProvider.createRefreshToken(principalDetails.getUser().getId(),principalDetails.getUser().getEmail(),refreshTokenExpireDt);

        tokenService.saveRefreshToken(principalDetails.getUser().getId(), refreshToken, refreshTokenExpireDt);

        HashMap<String,String> responseMap = new HashMap<>();
        responseMap.put("status","success");

        response.addHeader(JwtTokenInfo.getInfoByKey("header"),JwtTokenInfo.getInfoByKey("prefix") + token);
        response.addHeader(JwtTokenInfo.getInfoByKey("refreshHeader"),JwtTokenInfo.getInfoByKey("prefix") + refreshToken);
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String errorMessage = "";
        if(failed instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해주세요.";
        } else if (failed instanceof InternalAuthenticationServiceException) {
            errorMessage = "내부 시스템 문제로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요. ";
        } else if (failed instanceof UsernameNotFoundException) {
            errorMessage = "존재하지 않는 계정입니다. 회원가입 후 로그인해주세요.";
        } else if (failed instanceof AuthenticationCredentialsNotFoundException) {
            errorMessage = "인증 요청이 거부되었습니다. 관리자에게 문의하세요.";
        } else {
            errorMessage = "알 수 없는 오류로 로그인 요청을 처리할 수 없습니다. 관리자에게 문의하세요.";
        }

        HashMap<String,String> responseMap = new HashMap<>();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        responseMap.put("status","fail");
        responseMap.put("errorMessage",errorMessage);
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }
}
