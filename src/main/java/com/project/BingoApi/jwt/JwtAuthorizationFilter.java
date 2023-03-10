package com.project.BingoApi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.BingoApi.auth.JwtTokenInfo;
import com.project.BingoApi.auth.JwtTokenProvider;
import com.project.BingoApi.auth.PrincipalDetails;
import com.project.BingoApi.jpa.domain.RefreshToken;
import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.repository.TokenRepository;
import com.project.BingoApi.jpa.repository.UserRepository;
import com.project.BingoApi.jpa.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;
import java.util.Date;
import java.util.HashMap;

// 시큐리티가 filter가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 되어있음.
// 만약 권한이 인증이 필요한 주소가 아니면 안탐
public class JwtAuthorizationFilter  extends BasicAuthenticationFilter {

    private UserRepository userRepository;


    private TokenService tokenService;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository, TokenService tokenService){
        super(authenticationManager);
        this.userRepository = userRepository;
        this.tokenService = tokenService;

    }


    //인증이나 권한 요청이 필요한주소가 들어오면 타게되는 메소드
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(JwtTokenInfo.getInfoByKey("header"));

        if(jwtHeader == null || !jwtHeader.startsWith(JwtTokenInfo.getInfoByKey("prefix"))){
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = jwtHeader.replace(JwtTokenInfo.getInfoByKey("prefix"),"");

        String email = JwtTokenProvider.validToken(jwtToken, request);

        if(StringUtils.hasLength(email)){

            User user = userRepository.findByEmail(email);

            PrincipalDetails principalDetails = new PrincipalDetails(user);
            //jwt토큰 서명이 완료되면 객체생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());

            //강제로 시큐리티 세션에 접근하여 객체저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        chain.doFilter(request,response);

    }

}
