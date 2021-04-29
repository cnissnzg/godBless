package com.example.entity;

public class Course {
  private int idcourse;
  private String title;
  private int tid;
  private String tname;
  private String pic;
  public Course(){}
  public Course(int idcourse, String title, int tid, String tname, String pic) {
    this.idcourse = idcourse;
    this.title = title;
    this.tid = tid;
    this.tname = tname;
    this.pic = pic;
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

  public int getTid() {
    return tid;
  }

  public void setTid(int tid) {
    this.tid = tid;
  }

  public String getTname() {
    return tname;
  }

  public void setTname(String tname) {
    this.tname = tname;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  @Override
  public String toString() {
    return "Course{" +
            "idcourse=" + idcourse +
            ", title='" + title + '\'' +
            ", tid=" + tid +
            ", tname='" + tname + '\'' +
            ", pic='" + pic + '\'' +
            '}';
  }
}
