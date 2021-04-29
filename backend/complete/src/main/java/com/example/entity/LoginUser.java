package com.example.entity;

public class LoginUser {

  private String uid;
  private String pwd;
  private Integer rememberMe;
  private String verCode;

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public Integer getRememberMe() {
    return rememberMe;
  }

  public void setRememberMe(Integer rememberMe) {
    this.rememberMe = rememberMe;
  }

  public String getVerCode() {
    return verCode;
  }

  public void setVerCode(String verCode) {
    this.verCode = verCode;
  }
}