package com.example.entity;

public class Post {
  private int pid;
  private String poster;
  private int lastReplyNum;
  private String text;
  private String title;
  Post(){}
  public Post(String poster, int lastReplyNum, String text,String title) {
    this.poster = poster;
    this.lastReplyNum = lastReplyNum;
    this.text = text;
    this.title=title;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public String getPoster() {
    return poster;
  }

  public void setPoster(String poster) {
    this.poster = poster;
  }

  public int getLastReplyNum() {
    return lastReplyNum;
  }

  public void setLastReplyNum(int lastReplyNum) {
    this.lastReplyNum = lastReplyNum;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String toString() {
    return "Post{" +
            "pid=" + pid +
            ", poster='" + poster + '\'' +
            ", lastReplyNum=" + lastReplyNum +
            ", text='" + text + '\'' +
            '}';
  }
}
