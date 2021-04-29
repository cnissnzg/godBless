package com.example.util;

import org.springframework.security.authentication.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.www.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

  public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
    super(authenticationManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
                                  HttpServletResponse response,
                                  FilterChain chain) throws IOException, ServletException {
    String tokenHeader = request.getHeader(JwtTokenUtils.TOKEN_HEADER);
    // 如果请求头中没有Authorization信息则直接放行了
    if (tokenHeader == null || !tokenHeader.startsWith(JwtTokenUtils.TOKEN_PREFIX)) {
      chain.doFilter(request, response);
      return;
    }
    // 如果请求头中有token，则进行解析，并且设置认证信息
    SecurityContextHolder.getContext().setAuthentication(getAuthentication(tokenHeader));
    super.doFilterInternal(request, response, chain);
  }

  // 这里从token中获取用户信息并新建一个token
  private UsernamePasswordAuthenticationToken getAuthentication(String tokenHeader) {
    String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
    String username = JwtTokenUtils.getUsername(token);
    String role = JwtTokenUtils.getUserRole(token);
    System.out.println(role);
    if (username != null){
      return new UsernamePasswordAuthenticationToken(username, null,
              Collections.singleton(new SimpleGrantedAuthority(role))
      );
    }
    return null;
  }
}
