package com.example.entity;

public class Relation {
  private int idrelations;
  private String student;
  private String teacher;
  Relation(){}

  public Relation(String student, String teacher) {
    this.student = student;
    this.teacher = teacher;
  }

  public int getIdrelations() {
    return idrelations;
  }

  public void setIdrelations(int idrelations) {
    this.idrelations = idrelations;
  }

  public String getStudent() {
    return student;
  }

  public void setStudent(String student) {
    this.student = student;
  }

  public String getTeacher() {
    return teacher;
  }

  public void setTeacher(String teacher) {
    this.teacher = teacher;
  }

  @Override
  public String toString() {
    return "Relation{" +
            "idrelations=" + idrelations +
            ", student='" + student + '\'' +
            ", teacher='" + teacher + '\'' +
            '}';
  }
}
