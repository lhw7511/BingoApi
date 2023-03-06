package com.project.BingoApi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.project.BingoApi.auth.JwtTokenInfo;
import com.project.BingoApi.auth.PrincipalDetails;
import com.project.BingoApi.jpa.domain.User;
import com.project.BingoApi.jpa.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.SignatureException;

// 시큐리티가 filter가지고 있는데 그 필터중에 BasicAuthenticationFilter 라는 것이 있음.
// 권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 되어있음.
// 만약 권한이 인증이 필요한 주소가 아니면 안탐
public class JwtAuthorizationFilter  extends BasicAuthenticationFilter {

    private UserRepository userRepository;



    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;

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

        try{
            String email = JWT.require(Algorithm.HMAC512(JwtTokenInfo.getInfoByKey("secret"))).build().verify(jwtToken).getClaim("email").asString();

            if(email != null){
                User user = userRepository.findByEmail(email);

                PrincipalDetails principalDetails = new PrincipalDetails(user);
                //jwt토큰 서명이 완료되면 객체생성
                Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails,null,principalDetails.getAuthorities());

                //강제로 시큐리티 세션에 접근하여 객체저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (SignatureVerificationException | SignatureGenerationException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (TokenExpiredException e) {
                PrintWriter writer = null;
            try {
                writer = response.getWriter();
                writer.print("expire");
            } catch (Exception ee){
                logger.info(ee.getMessage());
            } finally {
                writer.close();
            }
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
