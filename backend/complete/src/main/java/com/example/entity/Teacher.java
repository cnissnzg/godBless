package com.example.entity;

public class Teacher {
  private String username;
  private Integer tid;
  private String name;
  private String academy;
  private String major;
  private String grade;
  Teacher(){}

  public Teacher(String username, Integer tid, String name, String academy, String major, String grade) {
    this.username = username;
    this.tid = tid;
    this.name = name;
    this.academy = academy;
    this.major = major;
    this.grade = grade;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Integer getTid() {
    return tid;
  }

  public void setTid(Integer tid) {
    this.tid = tid;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAcademy() {
    return academy;
  }

  public void setAcademy(String academy) {
    this.academy = academy;
  }

  public String getMajor() {
    return major;
  }

  public void setMajor(String major) {
    this.major = major;
  }

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  @Override
  public String toString() {
    return "Teacher{" +
            "username='" + username + '\'' +
            ", tid=" + tid +
            ", name='" + name + '\'' +
            ", academy='" + academy + '\'' +
            ", major='" + major + '\'' +
            ", grade='" + grade + '\'' +
            '}';
  }
}
