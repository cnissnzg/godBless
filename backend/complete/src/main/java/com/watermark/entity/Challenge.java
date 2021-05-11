package com.watermark.entity;

import java.util.*;

public class Challenge {
  private int pid;
  private String cid;
  private String author;
  private String ctime;
  private String mtime;
  private String describe;
  private String type;
  private String params;

  public Challenge() {
  }

  public Challenge(String cid, String author, String ctime, String mtime, String describe, String type, String params) {
    this.cid = cid;
    this.author = author;
    this.ctime = ctime;
    this.mtime = mtime;
    this.describe = describe;
    this.type = type;
    this.params = params;
  }

  public String getCid() {
    return cid;
  }

  public void setCid(String cid) {
    this.cid = cid;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getCtime() {
    return ctime;
  }

  public void setCtime(String ctime) {
    this.ctime = ctime;
  }

  public String getMtime() {
    return mtime;
  }

  public void setMtime(String mtime) {
    this.mtime = mtime;
  }

  public String getDescribe() {
    return describe;
  }

  public void setDescribe(String describe) {
    this.describe = describe;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }
}
