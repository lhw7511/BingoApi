package com.project.BingoApi.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.BingoApi.auth.PrincipalDetails;
import com.project.BingoApi.jpa.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;


//원래는 /login 요청해서 username,password를 post로전송하면 호출됨 근데 form로그인 disable해서안됨
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;


    // /login 요청을하면 로그인 시도를 위해서 실행됨
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // username,password 받아서 정상 계정인지 조회
        //authenticationManager로 시도하면 PrincipalDetailService의 loadUserByUsername 함수 실행됨
        //principaldetails를 세션에담아서 jwt리턴

        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(),user.getPassword());

            //principaldetailsservice loadUserByUsername함수가실행됨
            //password는 스프링에서 알아서 처리해줌
            Authentication authentication = authenticationManager.authenticate(token);
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

            //세션에 authentication저장
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //attemptAuthentication이후 정상인증시 실행됨
    //jwt토큰 생성후 클라이언트에게 jwt토큰을 응답해줌
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String token = JWT.create()
                .withSubject("bingo토큰")
                .withExpiresAt(new Date(System.currentTimeMillis()+(60000*10)))
                .withClaim("id",principalDetails.getUser().getId())
                .withClaim("email",principalDetails.getUser().getEmail())
                .sign(Algorithm.HMAC512("bingo"));
        response.addHeader("Authorization","Bearer "+token);

        System.out.println(token);
    }
}
