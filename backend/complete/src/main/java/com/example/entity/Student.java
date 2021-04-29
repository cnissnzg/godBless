package com.example.entity;

public class Student {
  private String username;
  private Integer sid;
  private String name;
  private String academy;
  private String major;
  private String grade;

  Student(){}

  public Student(String username, Integer sid, String name, String academy, String major, String grade) {
    this.username = username;
    this.sid = sid;
    this.name = name;
    this.academy = academy;
    this.major = major;
    this.grade = grade;
  }

  public Integer getSid() {
    return sid;
  }

  public void setSid(Integer sid) {
    this.sid = sid;
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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String toString() {
    return "Student{" +
            "sid=" + sid +
            ", name='" + name + '\'' +
            ", academy='" + academy + '\'' +
            ", major='" + major + '\'' +
            ", grade='" + grade + '\'' +
            '}';
  }
}
