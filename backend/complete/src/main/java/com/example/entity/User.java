package com.example.entity;

public class User {
  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", role='" + role + '\'' +
            '}';
  }
  public User(){}
  public User(Integer id, String username, String password,String role) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  private Integer id;
  private String username;
  private String password;
  private String role="";

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
