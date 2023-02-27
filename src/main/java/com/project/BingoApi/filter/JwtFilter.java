package com.project.BingoApi.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;


        if(("POST").equals(req.getMethod())){
            String headerAuth = req.getHeader("Authorization");
            System.out.println(headerAuth);
            if("cos".equals(headerAuth)){
                chain.doFilter(req,res);
            }else{
                PrintWriter out = res.getWriter();
                out.println("not auth");
                out.close();
            }
        }else{
            chain.doFilter(request,response);
        }




    }
}
