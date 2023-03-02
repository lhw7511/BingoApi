package com.project.BingoApi.auth;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
@Component
public class JwtTokenInfo{

    private static Map<String,String> tokenMap = new HashMap<>();


    private  String secret;

    private  String expiration;

    private  String prefix;

    private  String header;


    public JwtTokenInfo(@Value("${Jwt.secret}") String secret,
                        @Value("${Jwt.expiration}") String expiration,
                        @Value("${Jwt.prefix}") String prefix,
                        @Value("${Jwt.header}") String header) {
        this.secret = secret;
        this.expiration = expiration;
        this.prefix = prefix;
        this.header = header;
    }

    @PostConstruct
    public void setTokenMap(){
            tokenMap.put("secret",secret);
            tokenMap.put("expiration",expiration);
            tokenMap.put("prefix",prefix);
            tokenMap.put("header",header);
    }


    public static String getInfoByKey(String key){
        return tokenMap.get(key);
    }
}
