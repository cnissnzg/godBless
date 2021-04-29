package com.example.entity;

import java.util.*;

public class Exam {
  private int eid;
  private int problemNum;
  private int teacherId;
  private boolean usable;
  private String problemList;

  public Exam(){}

  public Exam(int problemNum, int teacherId, boolean usable,String problemList) {
    this.problemNum = problemNum;
    this.teacherId = teacherId;
    this.usable = usable;
    this.problemList = problemList;
  }

  public int getEid() {
    return eid;
  }

  public void setEid(int eid) {
    this.eid = eid;
  }

  public int getProblemNum() {
    return problemNum;
  }

  public void setProblemNum(int problemNum) {
    this.problemNum = problemNum;
  }

  public int getTeacherId() {
    return teacherId;
  }

  public void setTeacherId(int teacherId) {
    this.teacherId = teacherId;
  }

  public boolean isUsable() {
    return usable;
  }

  public void setUsable(boolean usable) {
    this.usable = usable;
  }

  public String getProblemList() {
    return problemList;
  }

  public void setProblemList(String problemList) {
    this.problemList = problemList;
  }

  @Override
  public String toString() {
    return "Exam{" +
            "eid=" + eid +
            ", problemNum=" + problemNum +
            ", teacherId=" + teacherId +
            ", usable=" + usable +
            ", problemList=" + problemList +
            '}';
  }
}
