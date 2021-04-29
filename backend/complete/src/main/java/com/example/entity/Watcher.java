package com.example.entity;

import java.io.*;

public class Watcher implements Serializable{
  private String idRoom;;
  private String username;

  public Watcher() {
  }

  public Watcher(String idRoom, String username) {
    this.idRoom = idRoom;
    this.username = username;
  }

  public String getIdRoom() {
    return idRoom;
  }

  public void setIdRoom(String idRoom) {
    this.idRoom = idRoom;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

}
