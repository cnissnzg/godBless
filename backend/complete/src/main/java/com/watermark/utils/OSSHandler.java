package com.watermark.utils;

import com.aliyun.oss.*;
import com.aliyun.oss.model.*;
import com.watermark.config.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.util.*;

import java.io.*;

public class OSSHandler {
  private static final String endpoint = "https://oss-cn-beijing.aliyuncs.com";
  private static final String accessKeyId = "";
  private static final String accessKeySecret = "";
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

  public static void save(String key,String value){
    try (PrintStream out = new PrintStream(new FileOutputStream(key))) {
      out.print(value);
    }catch (Exception e){
      e.printStackTrace();
    }
    try {
      File file = new File(key);
      InputStream fis = new FileInputStream(file);
      ossClient.putObject(bucketName, key, new ByteArrayInputStream(FileCopyUtils.copyToByteArray(fis)));
    }catch (Exception ex){
      ex.printStackTrace();
    }
  }

  public static String load(String key) throws Exception{
    ossClient.getObject(new GetObjectRequest(bucketName, key), new File(key));
    return FileUtils.readFile(key);
  }
}
