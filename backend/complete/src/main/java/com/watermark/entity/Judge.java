package com.watermark.entity;

import com.alibaba.fastjson.*;

import java.io.*;

public class Judge implements Serializable{
  private String runId;
  private String ip;
  private int port;
  private int algorithmId;
  private String fileName;
  private int pid;
  private String uid;
  private String callerUid;
  private int state;
  private long timeStamp;

  public Judge() {
    this.state=0;
    this.timeStamp=System.currentTimeMillis();
  }

  public String getRunId() {
    return runId;
  }

  public void setRunId(String runId) {
    this.runId = runId;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getAlgorithmId() {
    return algorithmId;
  }

  public void setAlgorithmId(int algorithmId) {
    this.algorithmId = algorithmId;
  }

  public String getUid() {
    return uid;
  }

  public void setUid(String uid) {
    this.uid = uid;
  }

  public int getState() {
    return state;
  }

  public void setState(int state) {
    this.state = state;
  }

  public long getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(long timeStamp) {
    this.timeStamp = timeStamp;
  }

  public int getPid() {
    return pid;
  }

  public void setPid(int pid) {
    this.pid = pid;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getCallerUid() {
    return callerUid;
  }

  public void setCallerUid(String callerUid) {
    this.callerUid = callerUid;
  }

  public String tranIp(){
    String[] strings = getIp().split("\\.");
    return strings[0]+"d"+strings[1]+"d"+strings[2]+"d"+strings[3];

  }
  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
