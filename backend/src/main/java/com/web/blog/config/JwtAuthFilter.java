package com.web.blog.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtAuthFilter extends GenericFilter {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Autowired
    JwtTokenProvider jwtToken;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //헤더에서 jwt받아오기
        String token = jwtToken.resolveToken((HttpServletRequest) request);
        
        //유효한 토큰인지 확인
        if(token != null && jwtToken.validateToken(token)){
            System.out.println("token: "+token);
            //토큰 유효하면 토큰에서 유저 정보 받아오기
            Authentication auth = jwtToken.getAuthentication(token);
            
            //SecurityContext에 Authentication 객체에 저장
            SecurityContextHolder.getContext().setAuthentication(auth);

        }
        
        chain.doFilter(request, response);
    }

    
}