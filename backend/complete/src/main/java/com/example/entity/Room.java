package com.example.entity;

public class Room {
  private int idroom;
  private String idteacher;
  private String title;
  private String password;
  private String teacherName;

  public Room() {
  }

  public Room(int idroom, String idteacher, String title, String password, String teacherName) {
    this.idroom = idroom;
    this.idteacher = idteacher;
    this.title = title;
    this.password = password;
    this.teacherName = teacherName;
  }

  public int getIdroom() {
    return idroom;
  }

  public void setIdroom(int idroom) {
    this.idroom = idroom;
  }

  public String getIdteacher() {
    return idteacher;
  }

  public void setIdteacher(String idteacher) {
    this.idteacher = idteacher;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  @Override
  public String toString() {
    return "Room{" +
            "idroom=" + idroom +
            ", idteacher='" + idteacher + '\'' +
            ", title='" + title + '\'' +
            ", password='" + password + '\'' +
            ", teacherName='" + teacherName + '\'' +
            '}';
  }
}
