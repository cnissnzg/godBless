package com.watermark.entity;

import java.util.*;

public class FileTree {
  private String name;
  private String type;
  private List<FileTree> children;

  public FileTree() {
  }

  public FileTree(String name, String type, List<FileTree> children) {
    this.name = name;
    this.type = type;
    this.children = children;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<FileTree> getChildren() {
    return children;
  }

  public void setChildren(List<FileTree> children) {
    this.children = children;
  }

  @Override
  public String toString() {
    return "FileTree{" +
            "name='" + name + '\'' +
            ", type='" + type + '\'' +
            ", children=" + children +
            '}';
  }
}
