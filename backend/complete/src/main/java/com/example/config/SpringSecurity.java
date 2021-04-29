package com.example.config;


import com.example.exception.*;
import com.watermark.service.*;
import com.example.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.method.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.web.cors.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurity extends WebSecurityConfigurerAdapter {
  @Autowired
  @Qualifier("userService")
  private UserDetailsService userDetailsService;

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder(){
    return new BCryptPasswordEncoder();
  }

  protected void configure(HttpSecurity http) throws Exception{
    http.cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers("/tasks/**").authenticated()
            //.antMatchers("/tasks/**","/api/v1/exams/**","/api/v1/problems/**",
                   // "/api/v1/posts/**","/api/v1/relations/**","/api/v1/reply/**",
                    //"/api/v1/student/**","/api/v1/submit/**","/api/v1/teacher/**").authenticated()
            //.antMatchers(HttpMethod.DELETE, "/tasks/**").hasAnyRole("ADMIN")
            .anyRequest().permitAll()
            .and()
            .addFilter(new JWTAuthenticationFilter(authenticationManager()))
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling().authenticationEntryPoint(new JWTAuthenticationEntryPoint());
  }

  CorsConfigurationSource corsConfigurationSource(){
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/*",new CorsConfiguration().applyPermitDefaultValues());
    return source;
  }
}
