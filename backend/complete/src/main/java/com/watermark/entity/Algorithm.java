package com.watermark.entity;

import java.util.*;

public class Algorithm {
  private String name;
  private HashMap<String,String> code;
  private FileTree fileTree;
  private List<KV> env;
  private List<KV> opt;
  private List<KV> dep;

  public Algorithm() {

  }

  public Algorithm(String name, HashMap<String, String> code, FileTree fileTree, List<KV> env, List<KV> opt, List<KV> dep) {
    this.name = name;
    this.code = code;
    this.fileTree = fileTree;
    this.env = env;
    this.opt = opt;
    this.dep = dep;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public HashMap<String, String> getCode() {
    return code;
  }

  public void setCode(HashMap<String, String> code) {
    this.code = code;
  }

  public FileTree getFileTree() {
    return fileTree;
  }

  public void setFileTree(FileTree fileTree) {
    this.fileTree = fileTree;
  }

  public List<KV> getEnv() {
    return env;
  }

  public void setEnv(List<KV> env) {
    this.env = env;
  }

  public List<KV> getOpt() {
    return opt;
  }

  public void setOpt(List<KV> opt) {
    this.opt = opt;
  }

  public List<KV> getDep() {
    return dep;
  }

  public void setDep(List<KV> dep) {
    this.dep = dep;
  }

  @Override
  public String toString() {
    return "Algorithm{" +
            "name='" + name + '\'' +
            ", code=" + code +
            ", fileTree=" + fileTree +
            ", env=" + env +
            ", opt=" + opt +
            ", dep=" + dep +
            '}';
  }
}
