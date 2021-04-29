package com.example.entity;

public class Problem {
  private int tid;
  private String textMain;
  private String textA;
  private String textB;
  private String textC;
  private String textD;
  private boolean A;
  private boolean B;
  private boolean C;
  private boolean D;

  public Problem(){}
  public Problem(String textMain, String textA, String textB, String textC, String textD, boolean A, boolean B, boolean C, boolean D) {
    this.textMain = textMain;
    this.textA = textA;
    this.textB = textB;
    this.textC = textC;
    this.textD = textD;
    this.A = A;
    this.B = B;
    this.C = C;
    this.D = D;
  }

  public int getTid() {
    return tid;
  }

  public void setTid(int tid) {
    this.tid = tid;
  }

  public String getTextMain() {
    return textMain;
  }

  public void setTextMain(String textMain) {
    this.textMain = textMain;
  }

  public String getTextA() {
    return textA;
  }

  public void setTextA(String textA) {
    this.textA = textA;
  }

  public String getTextB() {
    return textB;
  }

  public void setTextB(String textB) {
    this.textB = textB;
  }

  public String getTextC() {
    return textC;
  }

  public void setTextC(String textC) {
    this.textC = textC;
  }

  public String getTextD() {
    return textD;
  }

  public void setTextD(String textD) {
    this.textD = textD;
  }

  public boolean isA() {
    return A;
  }

  public void setA(boolean a) {
    A = a;
  }

  public boolean isB() {
    return B;
  }

  public void setB(boolean b) {
    B = b;
  }

  public boolean isC() {
    return C;
  }

  public void setC(boolean c) {
    C = c;
  }

  public boolean isD() {
    return D;
  }

  public void setD(boolean d) {
    D = d;
  }

  @Override
  public String toString() {
    return "Problem{" +
            "tid=" + tid +
            ", textMain='" + textMain + '\'' +
            ", textA='" + textA + '\'' +
            ", textB='" + textB + '\'' +
            ", textC='" + textC + '\'' +
            ", textD='" + textD + '\'' +
            ", A=" + A +
            ", B=" + B +
            ", C=" + C +
            ", D=" + D +
            '}';
  }
}
