package com.example.entity;

import java.io.*;

public class vDanmu implements Serializable{
  private int idvideo;
  private String who;
  private String content;
  private long time;

  public vDanmu(int idvideo, String who, String content, long time) {
    this.idvideo = idvideo;
    this.who = who;
    this.content = content;
    this.time = time;
  }

  public vDanmu() {
  }

  public int getIdvideo() {
    return idvideo;
  }

  public void setIdvideo(int idvideo) {
    this.idvideo = idvideo;
  }

  public String getWho() {
    return who;
  }

  public void setWho(String who) {
    this.who = who;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return "vDanmu{" +
            "idvideo=" + idvideo +
            ", who='" + who + '\'' +
            ", content='" + content + '\'' +
            ", time=" + time +
            '}';
  }
}
