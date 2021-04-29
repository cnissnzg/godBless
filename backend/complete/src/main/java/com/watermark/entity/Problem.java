package com.watermark.entity;

public class Problem {
  private int pid;
  private int uid;
  private int uCount;
  private int cCnt;
  private int pCnt;
  private double acPnt;
  private double acgPnt;
  private int acCnt;
  private String title;
  private int materialCnt;
  private int testCnt;

  public Problem(int pid, int uid, int uCount, int cCnt, int pCnt, double acPnt, double acgPnt, int acCnt, String title) {
    this.pid = pid;
    this.uid = uid;
    this.uCount = uCount;
    this.cCnt = cCnt;
    this.pCnt = pCnt;
    this.acPnt = acPnt;
    this.acgPnt = acgPnt;
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

  public int getpCnt() {
    return pCnt;
  }

  public void setpCnt(int pCnt) {
    this.pCnt = pCnt;
  }

  public double getAcPnt() {
    return acPnt;
  }

  public void setAcPnt(double acPnt) {
    this.acPnt = acPnt;
  }

  public double getAcgPnt() {
    return acgPnt;
  }

  public void setAcgPnt(double acgPnt) {
    this.acgPnt = acgPnt;
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
}
