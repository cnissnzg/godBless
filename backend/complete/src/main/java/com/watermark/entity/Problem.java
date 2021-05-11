package com.watermark.entity;

import java.util.*;

public class Problem {
  private int pid;
  private int uid;
  private int uCount;
  private int cCnt;
  private int acCnt;
  private String title;
  private int materialCnt;
  private int testCnt;

  private String description;
  private List<String> Tags;

  private int timeLimit;
  private int memoryLimit;
  private double bitErrorLimit;
  private double qualityLimit;

  public Problem(int pid, int uid, int uCount, int cCnt, int acCnt, String title) {
    this.pid = pid;
    this.uid = uid;
    this.uCount = uCount;
    this.cCnt = cCnt;
    this.acCnt = acCnt;
    this.title = title;
  }

  public Problem() {
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public int getUid() {
    return uid;
  }

  public void setUid(int uid) {
    this.uid = uid;
  }

  public int getuCount() {
    return uCount;
  }

  public void setuCount(int uCount) {
    this.uCount = uCount;
  }

  public int getcCnt() {
    return cCnt;
  }

  public void setcCnt(int cCnt) {
    this.cCnt = cCnt;
  }

  public int getAcCnt() {
    return acCnt;
  }

  public void setAcCnt(int acCnt) {
    this.acCnt = acCnt;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getMaterialCnt() {
    return materialCnt;
  }

  public void setMaterialCnt(int materialCnt) {
    this.materialCnt = materialCnt;
  }

  public int getTestCnt() {
    return testCnt;
  }

  public void setTestCnt(int testCnt) {
    this.testCnt = testCnt;
  }

  public List<String> getTags() {
    return Tags;
  }

  public void setTags(List<String> tags) {
    Tags = tags;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getTimeLimit() {
    return timeLimit;
  }

  public void setTimeLimit(int timeLimit) {
    this.timeLimit = timeLimit;
  }

  public int getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(int memoryLimit) {
    this.memoryLimit = memoryLimit;
  }

  public double getBitErrorLimit() {
    return bitErrorLimit;
  }

  public void setBitErrorLimit(double bitErrorLimit) {
    this.bitErrorLimit = bitErrorLimit;
  }

  public double getQualityLimit() {
    return qualityLimit;
  }

  public void setQualityLimit(double qualityLimit) {
    this.qualityLimit = qualityLimit;
  }
}
