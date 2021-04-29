package com.example.entity;

public class SubmitItem {
  private int submitId=0;
  private String username;
  private String timeStamp;
  private int eid=0;
  private int score=0;
  private String result;
  private String chosen;
  SubmitItem(){}

  public SubmitItem(int submitId, String username, String timeStamp, int eid, int score, String chosen) {
    this.submitId = submitId;
    this.username = username;
    this.timeStamp = timeStamp;
    this.eid = eid;
    this.score = score;
    this.chosen = chosen;
  }

  public int getSubmitId() {
    return submitId;
  }

  public void setSubmitId(int submitId) {
    this.submitId = submitId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public int getEid() {
    return eid;
  }

  public void setEid(int eid) {
    this.eid = eid;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public String getChosen() {
    return chosen;
  }

  public void setChosen(String chosen) {
    this.chosen = chosen;
  }

  @Override
  public String toString() {
    return "SubmitItem{" +
            "submitId=" + submitId +
            ", username='" + username + '\'' +
            ", timeStamp='" + timeStamp + '\'' +
            ", eid=" + eid +
            ", score=" + score +
            ", result='" + result + '\'' +
            ", chosen='" + chosen + '\'' +
            '}';
  }
}
