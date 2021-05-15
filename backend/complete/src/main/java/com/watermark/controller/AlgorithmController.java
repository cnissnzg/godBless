package com.watermark.controller;

import com.example.util.*;
import com.watermark.config.*;
import com.watermark.entity.*;
import com.watermark.utils.*;
import org.apache.ibatis.annotations.*;
import org.apache.rocketmq.client.exception.*;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.*;
import org.apache.rocketmq.remoting.exception.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.util.*;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.FileUtils;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping("/api/v1/watermark/algorithm")
public class AlgorithmController {
  @Autowired
  private MyConfig myConfig;
  @Autowired
  private HttpServletRequest request;
  @Autowired
  DefaultMQProducer defaultMQProducer;

  private void createDirs(FileTree fileTree,String dirname) throws Exception{
    for(FileTree tmp:fileTree.getChildren()){
      if(tmp.getType().equals("dir")){
        FileUtils.forceMkdir(new File(dirname+"/"+tmp.getName()));
        createDirs(tmp,dirname+"/"+tmp.getName());
      }
    }
  }
  @RequestMapping(value = "submit",method = RequestMethod.POST)
  public ResponseEntity<Integer> submit(@RequestBody Algorithm algorithm) throws Exception{
    String tokenHeader = request.getHeader("Authorization");
    String token = tokenHeader.replace(JwtTokenUtils.TOKEN_PREFIX, "");
    String dirname = myConfig.getRoot()+"/"+algorithm.getName()+"@"+JwtTokenUtils.getUsername(token);

    FileUtils.deleteQuietly(new File(dirname));
    FileUtils.forceMkdir(new File(dirname));
    createDirs(algorithm.getFileTree(),dirname);

    for(String key : algorithm.getCode().keySet()){
      File file = new File(dirname+key);
      FileWriter fileWriter = new FileWriter(file);
      fileWriter.write(algorithm.getCode().get(key));
      fileWriter.close();
    }
    CompressHelper.dirTarGzip(dirname,dirname+".tar.gz");


    return ResponseEntity.ok().build();
  }

  /**
   * 发送简单的MQ消息
   * @param msg
   * @return
   */
  @GetMapping("/send")
  public String send(String msg) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
    if (StringUtils.isEmpty(msg)) {
      return "200";
    }
    Message sendMsg = new Message("TestTopic", "TestTag", msg.getBytes());
    // 默认3秒超时
    SendResult sendResult = defaultMQProducer.send(sendMsg);
    return sendResult.toString();
  }
}
