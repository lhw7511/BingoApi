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
        HashMap<String,String> responseMap = new HashMap<>();
        String jwtHeader = request.getHeader(JwtTokenInfo.getInfoByKey("header"));

        if(jwtHeader == null || !jwtHeader.startsWith(JwtTokenInfo.getInfoByKey("prefix"))){
            chain.doFilter(request,response);
            return;
        }

        String jwtToken = jwtHeader.replace(JwtTokenInfo.getInfoByKey("prefix"),"");

        try{
            DecodedJWT decodeJwt = JWT.decode(jwtToken);
            if(decodeJwt.getExpiresAt().before(new Date())){
                String jwtRefreshHeader = request.getHeader(JwtTokenInfo.getInfoByKey("refreshHeader"));
                String jwtRefreshToken = jwtRefreshHeader.replace(JwtTokenInfo.getInfoByKey("prefix"),"");
                String userEmail = JWT.require(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret"))).build().verify(jwtRefreshToken).getClaim("email").asString();
                Long userId = JWT.require(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret"))).build().verify(jwtRefreshToken).getClaim("id").asLong();

                RefreshToken chkToken = tokenService.findById(userId);
                if(chkToken != null){
                    if(chkToken.getExpireDt().before(new Date())){
                        responseMap.put("status","logout");
                        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
                    }else{
                        String accessToken = JwtTokenProvider.createAccessToken(userId, userEmail, new Date(System.currentTimeMillis() + 60000));

                        User user = userRepository.findByEmail(userEmail);
                        PrincipalDetails principalDetails = new PrincipalDetails(user);
                        //jwt토큰 서명이 완료되면 객체생성
                        Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());
                        //강제로 시큐리티 세션에 접근하여 객체저장
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        response.addHeader(JwtTokenInfo.getInfoByKey("header"),JwtTokenInfo.getInfoByKey("prefix") + accessToken);
                    }

                }

            }else{
                String email = JWT.require(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret"))).build().verify(jwtToken).getClaim("email").asString();
                if(StringUtils.hasLength(email)){

                    User user = userRepository.findByEmail(email);

                    PrincipalDetails principalDetails = new PrincipalDetails(user);
                    //jwt토큰 서명이 완료되면 객체생성
                    Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());

                    //강제로 시큐리티 세션에 접근하여 객체저장
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }

        } catch (SignatureVerificationException | SignatureGenerationException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (TokenExpiredException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (IllegalArgumentException | JWTVerificationException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        } catch (Exception e){
            logger.info(e.getMessage());
        } finally {
            chain.doFilter(request,response);
        }


    }
}
