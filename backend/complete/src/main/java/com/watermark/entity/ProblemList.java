package com.watermark.entity;

import java.util.*;

public class ProblemList {
  private List<Problem> problems;
  private int total;

  public ProblemList() {
  }

  public ProblemList(List<Problem> problems, int total) {
    this.problems = problems;
    this.total = total;
  }

  public List<Problem> getProblems() {
    return problems;
  }

  public void setProblems(List<Problem> problems) {
    this.problems = problems;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }
}

