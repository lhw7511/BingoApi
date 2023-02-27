package com.project.BingoApi.config;

import com.project.BingoApi.filter.JwtFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;

@Configuration
public class FilterConfig {

    /*
    @Bean
    public FilterRegistrationBean<JwtFilter> jwtFilter(){
        FilterRegistrationBean<JwtFilter> bean = new FilterRegistrationBean<>(new JwtFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(0); //낮은 번호가 필터중에 가장 번저 실행
        return bean;
    }

     */
}
