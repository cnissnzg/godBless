package com.example.exception;

import com.fasterxml.jackson.databind.*;
import org.springframework.security.core.*;
import org.springframework.security.web.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest request,
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException, ServletException {

    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json; charset=utf-8");
    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    String reason = "统一处理，原因："+authException.getMessage();
    response.getWriter().write(new ObjectMapper().writeValueAsString(reason));
  }
}
