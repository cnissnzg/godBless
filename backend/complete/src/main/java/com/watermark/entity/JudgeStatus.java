package com.watermark.entity;

import java.util.*;

public class JudgeStatus {
  private String runId;
  private int type;
  private Map<String,String> detail;

  public JudgeStatus() {
  }

  public JudgeStatus(String runId, int type, Map<String,String> detail) {
    this.runId = runId;
    this.type = type;
    this.detail = detail;
  }

  public String getRunId() {
    return runId;
  }

  public void setRunId(String runId) {
    this.runId = runId;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public Map<String, String> getDetail() {
    return detail;
  }

  public void setDetail(Map<String, String> detail) {
    this.detail = detail;
  }

  @Override
  public String toString() {
    return "JudgeStatus{" +
            "runId='" + runId + '\'' +
            ", type=" + type +
            ", detail='" + detail + '\'' +
            '}';
  }
}
