package com.example.entity;

import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;
import com.watermark.entity.User;

public class JwtUser implements UserDetails {
  public JwtUser(String uid, String email, String password, Collection<? extends GrantedAuthority> authorities) {
    this.uid = uid;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  private String uid;
  private String email;
  private String password;
  private Collection<? extends GrantedAuthority> authorities;
  public JwtUser(){
  }
  public JwtUser(User user){
    uid = user.getUid();
    email = user.getEmail();
    password = user.getPassword();
    authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));

  }
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return uid;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
