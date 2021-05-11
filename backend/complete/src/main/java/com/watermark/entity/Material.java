package com.watermark.entity;

public class Material {
  private int mid;
  private int pid;
  private int type;
  private String filename;
  private String suffix;

  public Material() {
  }

  public Material(int mid, int pid, int type, String filename, String suffix) {
    this.mid = mid;
    this.pid = pid;
    this.type = type;
    this.filename = filename;
    this.suffix = suffix;
  }

  public int getMid() {
    return mid;
  }

  public void setMid(int mid) {
    this.mid = mid;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  public String getSuffix() {
    return suffix;
  }

  public void setSuffix(String suffix) {
    this.suffix = suffix;
  }
}
