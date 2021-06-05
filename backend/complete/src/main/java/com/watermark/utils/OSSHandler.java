package com.watermark.utils;

import com.aliyun.oss.*;
import com.watermark.config.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.util.*;

import java.io.*;

public class OSSHandler {
  private static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
  private static final String accessKeyId = "LTAI5tKfpvewxuAjFTzjZfh3";
  private static final String accessKeySecret = "ZjgDRdhsPOMiYSXq4sYjJ6xvvpdwOV";
  private static final String bucketName = "watermark-algorithm-code";
  private static OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

  public static void uploadCode(String path,String name){
    System.out.println(path+" "+name);
    try {
      File file = new File(path+"/"+name);
      InputStream fis = new FileInputStream(file);
      ossClient.putObject(bucketName, name, new ByteArrayInputStream(FileCopyUtils.copyToByteArray(fis)));
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }
}
