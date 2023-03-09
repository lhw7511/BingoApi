package com.project.BingoApi.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;


@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");
        if("TokenExpiredException".equals(exception)){
            setResponse(response,"tokenExpire");
        }else{
            setResponse(response,"tokenErr");
        }
    }

    private void setResponse(HttpServletResponse response,String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        HashMap<String,String> responseMap = new HashMap<>();
        responseMap.put("status","fail");
        responseMap.put("errorMessage",errorMessage);
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }


}
