package com.project.BingoApi.jpa.controller;


import com.project.BingoApi.auth.JwtTokenInfo;
import com.project.BingoApi.jpa.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;

    @PostMapping("reIssuanceToken")
    public HashMap<String,String> reIssuanceToken(HttpServletRequest request, HttpServletResponse response){
        HashMap<String,String> result = new HashMap<>();
        String refreshHeader = request.getHeader(JwtTokenInfo.getInfoByKey("refreshHeader"));
        String nwAccessToken = tokenService.ValidRefreshAndReIssuanceAccessToken(refreshHeader);
        if(StringUtils.hasLength(nwAccessToken)){
            result.put("status","success");
            response.addHeader(JwtTokenInfo.getInfoByKey("header"),JwtTokenInfo.getInfoByKey("prefix") + nwAccessToken);
        }else{
            result.put("status","fail");
        }
        return result;

    }
}

