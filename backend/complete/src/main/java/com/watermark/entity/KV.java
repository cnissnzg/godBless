package com.watermark.entity;

public class KV {
  private int key;
  private String name;
  private String value;

  public KV() {
  }

  public KV(int key, String name, String value) {
    this.key = key;
    this.name = name;
    this.value = value;
  }

  public int getKey() {
    return key;
  }

  public void setKey(int key) {
    this.key = key;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "KV{" +
            "key=" + key +
            ", name='" + name + '\'' +
            ", value='" + value + '\'' +
            '}';
  }
}
