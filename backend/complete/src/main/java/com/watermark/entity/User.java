package com.watermark.entity;

public class User {
  private String uid;
  private String password;
  private String email;
  private String role;

  public User(String uid, String password, String email,String role) {
    this.uid = uid;
    this.password = password;
    this.email = email;
    this.role = role;
  }

  public User() {
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public String getUsername() {
    return uid;
  }

  public void setUsername(String username) {
    this.uid = username;
  }
  @Override
  public String toString() {
    return "User{" +
            "uid=" + uid +
            ", password='" + password + '\'' +
            ", email='" + email + '\'' +
            '}';
  }
}
