package com.watermark.config;

import jdk.nashorn.internal.objects.annotations.*;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

@Component
@ConfigurationProperties(prefix = "my-path")
public class MyConfig {
  private String root;
  public String getRoot() {
    return root;
  }

  public void setRoot(String root) {
    this.root = root;
  }
}
