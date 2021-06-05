package com.watermark.entity;

import java.util.*;

public class CommonList<T> {
  private List<T> data;
  private int total;

  public CommonList() {
  }

  public CommonList(List<T> data, int total) {

    this.data = data;
    this.total = total;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}
