package com.example.entity;

public class Video {
  private int idvideo;
  private int idcourse;
  private String title;
  private String file;
  private String courseTitle;

  public Video(int idvideo, int idcourse, String title, String file) {
    this.idvideo = idvideo;
    this.idcourse = idcourse;
    this.title = title;
    this.file = file;
  }
  public Video(){}

  public int getIdvideo() {
    return idvideo;
  }

  public void setIdvideo(int idvideo) {
    this.idvideo = idvideo;
  }

  public int getIdcourse() {
    return idcourse;
  }

  public void setIdcourse(int idcourse) {
    this.idcourse = idcourse;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFile() {
    return file;
  }

  public void setFile(String file) {
    this.file = file;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public void setCourseTitle(String courseTitle) {
    this.courseTitle = courseTitle;
  }

  @Override
  public String toString() {
    return "Video{" +
            "idvideo=" + idvideo +
            ", idcourse=" + idcourse +
            ", title='" + title + '\'' +
            ", file='" + file + '\'' +
            '}';
  }
}

