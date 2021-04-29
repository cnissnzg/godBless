package com.example.entity;

public class Comment {
  private int idcomment;
  private String idRoom;
  private long time;
  private String username;
  private String content;

  public Comment() {
  }

  public Comment(int idcomment, String idRoom, long time, String username, String content) {
    this.idcomment = idcomment;
    this.idRoom = idRoom;
    this.time = time;
    this.username = username;
    this.content = content;
  }

  public String getIdRoom() {
    return idRoom;
  }

  public void setIdRoom(String idRoom) {
    this.idRoom = idRoom;
  }

  public int getIdcomment() {
    return idcomment;
  }

  public void setIdcomment(int idcomment) {
    this.idcomment = idcomment;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
}
